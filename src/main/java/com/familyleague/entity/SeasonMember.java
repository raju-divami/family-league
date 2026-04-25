package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "season_members",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "user_id"}))
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
    private String status = "ACTIVE";

    public SeasonMember() {
    }

    public SeasonMember(Long id, LeagueSeason season, User user, LocalDateTime joinedAt, String status) {
        this.id = id;
        this.season = season;
        this.user = user;
        this.joinedAt = joinedAt;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LeagueSeason season;
        private User user;
        private LocalDateTime joinedAt;
        private String status = "ACTIVE";

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

        public Builder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public SeasonMember build() {
            SeasonMember obj = new SeasonMember();
            obj.id = this.id;
            obj.season = this.season;
            obj.user = this.user;
            obj.joinedAt = this.joinedAt;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
