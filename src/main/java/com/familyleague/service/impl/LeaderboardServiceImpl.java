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
        
        // Reset all leaderboard entries for this season
        List<Leaderboard> leaderboardEntries = leaderboardRepository.findBySeasonId(seasonId);
        leaderboardEntries.forEach(entry -> entry.setTotalPoints(0));
        
        // Get all completed matches with results
        List<MatchResult> results = resultRepository.findByMatchSeasonId(seasonId);
        
        for (MatchResult result : results) {
            List<MatchPrediction> predictions = predictionRepository.findByMatchId(result.getMatch().getId());
            
            for (MatchPrediction prediction : predictions) {
                int points = calculatePoints(prediction, result);
                
                if (points > 0) {
                    // Update leaderboard
                    Leaderboard entry = leaderboardRepository
                            .findBySeasonIdAndUserId(seasonId, prediction.getUser().getId())
                            .orElseGet(() -> Leaderboard.builder()
                                    .season(result.getMatch().getSeason())
                                    .user(prediction.getUser())
                                    .totalPoints(0)
                                    .rankNo(0)
                                    .build());
                    
                    entry.setTotalPoints(entry.getTotalPoints() + points);
                    leaderboardRepository.save(entry);
                    
                    // Create point transaction
                    PointTransaction transaction = PointTransaction.builder()
                            .user(prediction.getUser())
                            .season(result.getMatch().getSeason())
                            .sourceType("MATCH_PREDICTION")
                            .sourceId(result.getMatch().getId())
                            .ruleCode(points == 10 ? "EXACT_WINNER" : points == 5 ? "WINNER_ONLY" : "TOSS_WINNER")
                            .points(points)
                            .build();
                    pointTransactionRepository.save(transaction);
                }
            }
        }
        
        // Update ranks
        List<Leaderboard> sortedEntries = leaderboardRepository.findBySeasonIdOrderByTotalPointsDesc(seasonId);
        int rank = 1;
        for (Leaderboard entry : sortedEntries) {
            entry.setRankNo(rank++);
            leaderboardRepository.save(entry);
        }
        
        log.info("Leaderboard recalculation completed for season: {}", seasonId);
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
    
    private int calculatePoints(MatchPrediction prediction, MatchResult result) {
        int points = 0;
        
        // Winner prediction + Toss winner: 10 points
        if (prediction.getPredictedWinnerTeam() != null && result.getWinnerTeam() != null &&
                prediction.getPredictedWinnerTeam().getId().equals(result.getWinnerTeam().getId()) &&
                prediction.getPredictedTossTeam() != null && result.getTossWinnerTeam() != null &&
                prediction.getPredictedTossTeam().getId().equals(result.getTossWinnerTeam().getId())) {
            points = 10;
        }
        // Winner prediction only: 5 points
        else if (prediction.getPredictedWinnerTeam() != null && result.getWinnerTeam() != null &&
                prediction.getPredictedWinnerTeam().getId().equals(result.getWinnerTeam().getId())) {
            points = 5;
        }
        // Toss winner only: 2 points
        else if (prediction.getPredictedTossTeam() != null && result.getTossWinnerTeam() != null &&
                prediction.getPredictedTossTeam().getId().equals(result.getTossWinnerTeam().getId())) {
            points = 2;
        }
        
        return points;
    }
}
