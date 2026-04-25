package com.familyleague.entity;

import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.Match;
import com.familyleague.entity.Player;
import com.familyleague.entity.Team;
import com.familyleague.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_predictions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MatchPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_winner_team_id")
    private Team predictedWinnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_toss_team_id")
    private Team predictedTossTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_player_id")
    private Player predictedPlayer;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "status", length = 30)
    @Builder.Default
    private String status = "SUBMITTED";
}
