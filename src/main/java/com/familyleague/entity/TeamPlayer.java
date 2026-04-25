package com.familyleague.entity;

import com.familyleague.entity.SeasonTeam;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_players",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_team_id", "player_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TeamPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_team_id", nullable = false)
    private SeasonTeam seasonTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
