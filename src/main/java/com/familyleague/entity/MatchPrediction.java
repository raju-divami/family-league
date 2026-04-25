package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_predictions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "user_id"}))
public class MatchPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_winner_team_id")
    private Team predictedWinnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_toss_team_id")
    private Team predictedTossTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predicted_player_id")
    private Player predictedPlayer;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "status", length = 30)
    private String status = "SUBMITTED";

    public MatchPrediction() {
    }

    public MatchPrediction(Long id, Match match, LeagueSeason season, User user, Team predictedWinnerTeam, Team predictedTossTeam, Player predictedPlayer, LocalDateTime submittedAt, LocalDateTime lockedAt, String status) {
        this.id = id;
        this.match = match;
        this.season = season;
        this.user = user;
        this.predictedWinnerTeam = predictedWinnerTeam;
        this.predictedTossTeam = predictedTossTeam;
        this.predictedPlayer = predictedPlayer;
        this.submittedAt = submittedAt;
        this.lockedAt = lockedAt;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Match match;
        private LeagueSeason season;
        private User user;
        private Team predictedWinnerTeam;
        private Team predictedTossTeam;
        private Player predictedPlayer;
        private LocalDateTime submittedAt;
        private LocalDateTime lockedAt;
        private String status = "SUBMITTED";

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder match(Match match) {
            this.match = match;
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

        public Builder predictedWinnerTeam(Team predictedWinnerTeam) {
            this.predictedWinnerTeam = predictedWinnerTeam;
            return this;
        }

        public Builder predictedTossTeam(Team predictedTossTeam) {
            this.predictedTossTeam = predictedTossTeam;
            return this;
        }

        public Builder predictedPlayer(Player predictedPlayer) {
            this.predictedPlayer = predictedPlayer;
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

        public MatchPrediction build() {
            MatchPrediction obj = new MatchPrediction();
            obj.id = this.id;
            obj.match = this.match;
            obj.season = this.season;
            obj.user = this.user;
            obj.predictedWinnerTeam = this.predictedWinnerTeam;
            obj.predictedTossTeam = this.predictedTossTeam;
            obj.predictedPlayer = this.predictedPlayer;
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

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
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

    public Team getPredictedWinnerTeam() {
        return predictedWinnerTeam;
    }

    public void setPredictedWinnerTeam(Team predictedWinnerTeam) {
        this.predictedWinnerTeam = predictedWinnerTeam;
    }

    public Team getPredictedTossTeam() {
        return predictedTossTeam;
    }

    public void setPredictedTossTeam(Team predictedTossTeam) {
        this.predictedTossTeam = predictedTossTeam;
    }

    public Player getPredictedPlayer() {
        return predictedPlayer;
    }

    public void setPredictedPlayer(Player predictedPlayer) {
        this.predictedPlayer = predictedPlayer;
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
