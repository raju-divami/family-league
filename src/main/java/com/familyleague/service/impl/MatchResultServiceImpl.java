package com.familyleague.service.impl;

import com.familyleague.dto.request.PublishResultRequest;
import com.familyleague.dto.response.MatchResultResponse;
import com.familyleague.entity.Match;
import com.familyleague.entity.MatchResult;
import com.familyleague.entity.Player;
import com.familyleague.entity.Team;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.MatchResultMapper;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.MatchResultRepository;
import com.familyleague.repository.PlayerRepository;
import com.familyleague.repository.TeamRepository;
import com.familyleague.service.LeaderboardService;
import com.familyleague.service.MatchResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService {
    
    private final MatchResultRepository resultRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final MatchResultMapper resultMapper;
    private final LeaderboardService leaderboardService;

    @Override
    @Transactional
    public MatchResultResponse publishResult(Long matchId, PublishResultRequest request, Long adminId) {
        log.debug("Publishing result for match: {}", matchId);
        
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        
        MatchResult.MatchResultBuilder builder = resultRepository.findByMatchId(matchId)
                .map(r -> MatchResult.builder()
                        .id(r.getId())
                        .match(r.getMatch()))
                .orElse(MatchResult.builder().match(match));
        
        if (request.getTossWinnerTeamId() != null) {
            Team tossWinner = teamRepository.findById(request.getTossWinnerTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Toss winner team not found"));
            builder.tossWinnerTeam(tossWinner);
        }
        
        if (request.getWinnerTeamId() != null) {
            Team winner = teamRepository.findById(request.getWinnerTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Winner team not found"));
            builder.winnerTeam(winner);
        }
        
        if (request.getPlayerOfMatchId() != null) {
            Player player = playerRepository.findById(request.getPlayerOfMatchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
            builder.playerOfMatch(player);
        }
        
        builder.tie(request.isTie())
                .remarks(request.getRemarks())
                .publishedBy(adminId)
                .publishedAt(LocalDateTime.now());
        
        MatchResult result = resultRepository.save(builder.build());
        match.setStatus("COMPLETED");
        matchRepository.save(match);
        
        // Trigger async leaderboard recalculation
        recalculateLeaderboardAsync(match.getSeason().getId());
        
        log.info("Match result published for match: {}", matchId);
        return resultMapper.toResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public MatchResultResponse getResultByMatchId(Long matchId) {
        MatchResult result = resultRepository.findByMatchId(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found for match: " + matchId));
        return resultMapper.toResponse(result);
    }
    
    @Async
    protected void recalculateLeaderboardAsync(Long seasonId) {
        try {
            leaderboardService.recalculateLeaderboard(seasonId);
        } catch (Exception e) {
            log.error("Error recalculating leaderboard for season: {}", seasonId, e);
        }
    }
}
