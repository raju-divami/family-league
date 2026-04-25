package com.familyleague.entity;

import com.familyleague.entity.Player;
import com.familyleague.entity.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", unique = true, nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toss_winner_team_id")
    private Team tossWinnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_of_match_id")
    private Player playerOfMatch;

    @Column(name = "is_tie")
    @Builder.Default
    private boolean tie = false;

    @Column(name = "remarks", columnDefinition = "text")
    private String remarks;

    @Column(name = "published_by")
    private Long publishedBy;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}
