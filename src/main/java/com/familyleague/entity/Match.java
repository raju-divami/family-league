package com.familyleague.entity;

import com.familyleague.entity.BaseEntity;
import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @Column(name = "match_no")
    private Integer matchNo;

    @Column(name = "stage", length = 50)
    @Builder.Default
    private String stage = "LEAGUE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @Column(name = "venue", length = 150)
    private String venue;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "prediction_lock_time", nullable = false)
    private LocalDateTime predictionLockTime;

    @Column(name = "status", length = 30)
    @Builder.Default
    private String status = "SCHEDULED";
}
