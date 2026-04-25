package com.familyleague.service.impl;

import com.familyleague.dto.response.LeaderboardResponse;
import com.familyleague.dto.response.PointTransactionResponse;
import com.familyleague.entity.Leaderboard;
import com.familyleague.entity.MatchPrediction;
import com.familyleague.entity.MatchResult;
import com.familyleague.entity.PointTransaction;
import com.familyleague.mapper.LeaderboardMapper;
import com.familyleague.mapper.PointTransactionMapper;
import com.familyleague.repository.LeaderboardRepository;
import com.familyleague.repository.MatchPredictionRepository;
import com.familyleague.repository.MatchResultRepository;
import com.familyleague.repository.PointTransactionRepository;
import com.familyleague.repository.SeasonMemberRepository;
import com.familyleague.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {
    
    private final LeaderboardRepository leaderboardRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final MatchPredictionRepository predictionRepository;
    private final MatchResultRepository resultRepository;
    private final SeasonMemberRepository seasonMemberRepository;
    private final LeaderboardMapper leaderboardMapper;
    private final PointTransactionMapper pointTransactionMapper;

    @Override
    @Transactional
    public void recalculateLeaderboard(Long seasonId) {
        log.info("Recalculating leaderboard for season: {}", seasonId);
        
        // Clear existing point transactions for this season
        pointTransactionRepository.deleteBySeasonId(seasonId);
        
        // Reset all leaderboard entries for this season
        List<Leaderboard> leaderboardEntries = leaderboardRepository.findBySeasonId(seasonId);
        leaderboardEntries.forEach(entry -> {
            entry.setTotalPoints(0);
            entry.setRecalculatedAt(java.time.LocalDateTime.now());
        });
        
        // Get all completed matches with results
        List<MatchResult> results = resultRepository.findByMatchSeasonId(seasonId);
        
        for (MatchResult result : results) {
            List<MatchPrediction> predictions = predictionRepository.findByMatchId(result.getMatch().getId());
            
            for (MatchPrediction prediction : predictions) {
                int pointsEarned = 0;
                
                // Check winner prediction (1 point)
                if (prediction.getPredictedWinnerTeam() != null && result.getWinnerTeam() != null &&
                        prediction.getPredictedWinnerTeam().getId().equals(result.getWinnerTeam().getId())) {
                    pointsEarned++;
                    createPointTransaction(prediction, result, "MATCH_WINNER", 1);
                }
                
                // Check toss winner prediction (1 point)
                if (prediction.getPredictedTossTeam() != null && result.getTossWinnerTeam() != null &&
                        prediction.getPredictedTossTeam().getId().equals(result.getTossWinnerTeam().getId())) {
                    pointsEarned++;
                    createPointTransaction(prediction, result, "TOSS_WINNER", 1);
                }
                
                // Check player of match prediction (1 point)
                if (prediction.getPredictedPlayer() != null && result.getPlayerOfMatch() != null &&
                        prediction.getPredictedPlayer().getId().equals(result.getPlayerOfMatch().getId())) {
                    pointsEarned++;
                    createPointTransaction(prediction, result, "PLAYER_OF_MATCH", 1);
                }
                
                // Handle tie scenarios: if match is a tie and user predicted tie, award 1 point
                // (This is in addition to other predictions)
                if (result.isTie() && prediction.getPredictedWinnerTeam() == null) {
                    // User predicted a tie by not selecting a winner
                    pointsEarned++;
                    createPointTransaction(prediction, result, "TIE_PREDICTION", 1);
                }
                
                // Update leaderboard entry
                if (pointsEarned > 0) {
                    Leaderboard entry = leaderboardRepository
                            .findBySeasonIdAndUserId(seasonId, prediction.getUser().getId())
                            .orElseGet(() -> Leaderboard.builder()
                                    .season(result.getMatch().getSeason())
                                    .user(prediction.getUser())
                                    .totalPoints(0)
                                    .rankNo(0)
                                    .build());
                    
                    entry.setTotalPoints(entry.getTotalPoints() + pointsEarned);
                    entry.setRecalculatedAt(java.time.LocalDateTime.now());
                    leaderboardRepository.save(entry);
                }
            }
        }
        
        // Update ranks based on total points (higher points = better rank)
        List<Leaderboard> sortedEntries = leaderboardRepository.findBySeasonIdOrderByTotalPointsDesc(seasonId);
        int rank = 1;
        for (Leaderboard entry : sortedEntries) {
            entry.setRankNo(rank++);
            entry.setRecalculatedAt(java.time.LocalDateTime.now());
            leaderboardRepository.save(entry);
        }
        
        log.info("Leaderboard recalculation completed for season: {} with {} entries", seasonId, sortedEntries.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaderboardResponse> getLeaderboard(Long seasonId) {
        List<Leaderboard> leaderboard = leaderboardRepository.findBySeasonIdOrderByRankNoAsc(seasonId);
        return leaderboard.stream().map(leaderboardMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointTransactionResponse> getPointHistory(Long seasonId, Long userId) {
        List<PointTransaction> transactions = pointTransactionRepository.findBySeasonIdAndUserIdOrderByCreatedAtDesc(seasonId, userId);
        return transactions.stream().map(pointTransactionMapper::toResponse).toList();
    }
    
    /**
     * Helper method to create a point transaction record
     */
    private void createPointTransaction(MatchPrediction prediction, MatchResult result, String ruleCode, int points) {
        PointTransaction transaction = PointTransaction.builder()
                .user(prediction.getUser())
                .season(result.getMatch().getSeason())
                .sourceType("MATCH")
                .sourceId(result.getMatch().getId())
                .ruleCode(ruleCode)
                .points(points)
                .build();
        pointTransactionRepository.save(transaction);
    }
}
