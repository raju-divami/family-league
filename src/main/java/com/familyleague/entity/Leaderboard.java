package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboards",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "user_id"}))
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
    private Integer totalPoints = 0;

    @Column(name = "rank_no")
    private Integer rankNo;

    @Column(name = "recalculated_at")
    private LocalDateTime recalculatedAt;

    public Leaderboard() {
    }

    public Leaderboard(Long id, LeagueSeason season, User user, Integer totalPoints, Integer rankNo, LocalDateTime recalculatedAt) {
        this.id = id;
        this.season = season;
        this.user = user;
        this.totalPoints = totalPoints;
        this.rankNo = rankNo;
        this.recalculatedAt = recalculatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LeagueSeason season;
        private User user;
        private Integer totalPoints = 0;
        private Integer rankNo;
        private LocalDateTime recalculatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder season(LeagueSeason season) {
            this.season = season;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder totalPoints(Integer totalPoints) {
            this.totalPoints = totalPoints;
            return this;
        }

        public Builder rankNo(Integer rankNo) {
            this.rankNo = rankNo;
            return this;
        }

        public Builder recalculatedAt(LocalDateTime recalculatedAt) {
            this.recalculatedAt = recalculatedAt;
            return this;
        }

        public Leaderboard build() {
            Leaderboard obj = new Leaderboard();
            obj.id = this.id;
            obj.season = this.season;
            obj.user = this.user;
            obj.totalPoints = this.totalPoints;
            obj.rankNo = this.rankNo;
            obj.recalculatedAt = this.recalculatedAt;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeagueSeason getSeason() {
        return season;
    }

    public void setSeason(LeagueSeason season) {
        this.season = season;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getRankNo() {
        return rankNo;
    }

    public void setRankNo(Integer rankNo) {
        this.rankNo = rankNo;
    }

    public LocalDateTime getRecalculatedAt() {
        return recalculatedAt;
    }

    public void setRecalculatedAt(LocalDateTime recalculatedAt) {
        this.recalculatedAt = recalculatedAt;
    }
}
