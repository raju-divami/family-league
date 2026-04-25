package com.familyleague.entity;

import com.familyleague.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "season_prediction_positions",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"prediction_id", "rank_position"}),
           @UniqueConstraint(columnNames = {"prediction_id", "team_id"})
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SeasonPredictionPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prediction_id", nullable = false)
    private SeasonPrediction prediction;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
