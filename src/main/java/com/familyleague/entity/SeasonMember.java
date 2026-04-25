package com.familyleague.entity;

import com.familyleague.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "season_members",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SeasonMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "status", length = 30)
    @Builder.Default
    private String status = "ACTIVE";
}
