package com.familyleague.entity;

import com.familyleague.entity.LeagueSeason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "season_teams",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "team_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SeasonTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "seed_rank")
    private Integer seedRank;
}
