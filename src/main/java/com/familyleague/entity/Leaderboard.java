package com.familyleague.entity;

import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboards",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_points")
    @Builder.Default
    private Integer totalPoints = 0;

    @Column(name = "rank_no")
    private Integer rankNo;

    @Column(name = "recalculated_at")
    private LocalDateTime recalculatedAt;
}
