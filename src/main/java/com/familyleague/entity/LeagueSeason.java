package com.familyleague.entity;

import com.familyleague.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "league_seasons",
       uniqueConstraints = @UniqueConstraint(columnNames = {"league_id", "season_code"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueSeason extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "season_code", length = 50, nullable = false)
    private String seasonCode;

    @Column(name = "season_name", length = 150, nullable = false)
    private String seasonName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "prediction_lock_hours")
    @Builder.Default
    private Integer predictionLockHours = 4;

    @Column(name = "match_prediction_lock_hours")
    @Builder.Default
    private Integer matchPredictionLockHours = 1;

    @Column(name = "status", length = 30)
    @Builder.Default
    private String status = "DRAFT";

    @Column(name = "first_match_start_time")
    private LocalDateTime firstMatchStartTime;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}
