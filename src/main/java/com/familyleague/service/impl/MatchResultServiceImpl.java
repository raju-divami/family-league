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
import com.familyleague.service.EmailService;
import com.familyleague.service.LeaderboardService;
import com.familyleague.service.MatchResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MatchResultServiceImpl implements MatchResultService {

    private static final Logger log = LoggerFactory.getLogger(MatchResultServiceImpl.class);

    private final MatchResultRepository resultRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final MatchResultMapper resultMapper;
    private final LeaderboardService leaderboardService;
    private final EmailService emailService;

    @Value("${app.admin.email:admin@familyleague.com}")
    private String adminEmail;

    public MatchResultServiceImpl(MatchResultRepository resultRepository,
                                  MatchRepository matchRepository,
                                  TeamRepository teamRepository,
                                  PlayerRepository playerRepository,
                                  MatchResultMapper resultMapper,
                                  LeaderboardService leaderboardService,
                                  EmailService emailService) {
        this.resultRepository = resultRepository;
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.resultMapper = resultMapper;
        this.leaderboardService = leaderboardService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public MatchResultResponse publishResult(Long matchId, PublishResultRequest request, Long adminId) {
        log.debug("Publishing result for match: {}", matchId);

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));

        MatchResult.Builder builder = resultRepository.findByMatchId(matchId)
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

        builder.tie(request.getIsTie() != null ? request.getIsTie() : false)
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

    /**
     * Asynchronously recalculates the leaderboard and sends email notification to admin.
     * Executes in a separate thread using the configured task executor.
     *
     * @param seasonId The season ID for which to recalculate the leaderboard
     */
    @Async
    protected void recalculateLeaderboardAsync(Long seasonId) {
        try {
            log.info("Starting async leaderboard recalculation for season: {}", seasonId);
            leaderboardService.recalculateLeaderboard(seasonId);

            // Send success notification to admin
            String subject = "Leaderboard Recalculation Completed - Season " + seasonId;
            String body = String.format(
                "Hello Admin,\n\n" +
                "The leaderboard for Season %d has been successfully recalculated.\n\n" +
                "Timestamp: %s\n\n" +
                "Best regards,\n" +
                "Family League System",
                seasonId,
                LocalDateTime.now()
            );

            emailService.sendEmailAsync(adminEmail, subject, body);
            log.info("Leaderboard recalculation completed and admin notified for season: {}", seasonId);

        } catch (Exception e) {
            log.error("Error recalculating leaderboard for season: {}", seasonId, e);

            // Send failure notification to admin
            String subject = "Leaderboard Recalculation Failed - Season " + seasonId;
            String body = String.format(
                "Hello Admin,\n\n" +
                "The leaderboard recalculation for Season %d has failed.\n\n" +
                "Error: %s\n" +
                "Timestamp: %s\n\n" +
                "Please check the system logs for more details.\n\n" +
                "Best regards,\n" +
                "Family League System",
                seasonId,
                e.getMessage(),
                LocalDateTime.now()
            );

            emailService.sendEmailAsync(adminEmail, subject, body);
        }
    }
}
