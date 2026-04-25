package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "season_predictions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "user_id"}))
public class SeasonPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "status", length = 30)
    private String status = "SUBMITTED";

    public SeasonPrediction() {
    }

    public SeasonPrediction(Long id, LeagueSeason season, User user, LocalDateTime submittedAt, LocalDateTime lockedAt, String status) {
        this.id = id;
        this.season = season;
        this.user = user;
        this.submittedAt = submittedAt;
        this.lockedAt = lockedAt;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LeagueSeason season;
        private User user;
        private LocalDateTime submittedAt;
        private LocalDateTime lockedAt;
        private String status = "SUBMITTED";

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

        public Builder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public Builder lockedAt(LocalDateTime lockedAt) {
            this.lockedAt = lockedAt;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public SeasonPrediction build() {
            SeasonPrediction obj = new SeasonPrediction();
            obj.id = this.id;
            obj.season = this.season;
            obj.user = this.user;
            obj.submittedAt = this.submittedAt;
            obj.lockedAt = this.lockedAt;
            obj.status = this.status;
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

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
