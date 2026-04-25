package com.familyleague.scheduler;

import com.familyleague.entity.Match;
import com.familyleague.entity.SeasonMember;
import com.familyleague.repository.MatchPredictionRepository;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.SeasonMemberRepository;
import com.familyleague.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sends prediction reminder emails to season members who have not yet submitted
 * a prediction for matches whose lock window is approaching.
 */
@Component
public class MatchReminderScheduler {

    private static final Logger log = LoggerFactory.getLogger(MatchReminderScheduler.class);

    private final MatchRepository matchRepository;
    private final SeasonMemberRepository seasonMemberRepository;
    private final MatchPredictionRepository matchPredictionRepository;
    private final NotificationService notificationService;

    @Value("${app.scheduler.reminder-window-hours:2}")
    private int reminderWindowHours;

    public MatchReminderScheduler(MatchRepository matchRepository,
                                  SeasonMemberRepository seasonMemberRepository,
                                  MatchPredictionRepository matchPredictionRepository,
                                  NotificationService notificationService) {
        this.matchRepository = matchRepository;
        this.seasonMemberRepository = seasonMemberRepository;
        this.matchPredictionRepository = matchPredictionRepository;
        this.notificationService = notificationService;
    }

    /**
     * Runs every 15 minutes (configurable via app.scheduler.reminder-interval-ms).
     * Finds SCHEDULED matches whose prediction lock time falls within the next
     * {@code reminderWindowHours} hours and emails members who haven't predicted.
     */
    @Scheduled(fixedDelayString = "${app.scheduler.reminder-interval-ms:900000}")
    public void sendPredictionReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowEnd = now.plusHours(reminderWindowHours);

        List<Match> lockingMatches = matchRepository
                .findByStatusAndPredictionLockTimeBetweenAndDeletedFalse("SCHEDULED", now, windowEnd);

        if (lockingMatches.isEmpty()) {
            log.debug("No matches approaching lock window — skipping reminder run");
            return;
        }

        log.info("Reminder scheduler: {} match(es) lock within {} hour(s)",
                lockingMatches.size(), reminderWindowHours);

        for (Match match : lockingMatches) {
            processMatchReminders(match);
        }
    }

    private void processMatchReminders(Match match) {
        Long seasonId = match.getSeason().getId();
        List<SeasonMember> activeMembers = seasonMemberRepository.findBySeasonIdAndStatus(seasonId, "ACTIVE");

        int reminded = 0;
        for (SeasonMember member : activeMembers) {
            Long userId = member.getUser().getId();
            if (!matchPredictionRepository.existsByMatchIdAndUserId(match.getId(), userId)) {
                notificationService.sendPredictionReminderToUser(match.getId(), userId);
                reminded++;
            }
        }

        log.info("Match {}: sent {} reminder(s) to users without predictions", match.getId(), reminded);
    }
}
