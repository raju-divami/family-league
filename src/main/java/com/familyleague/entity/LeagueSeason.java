package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "league_seasons",
       uniqueConstraints = @UniqueConstraint(columnNames = {"league_id", "season_code"}))
public class LeagueSeason extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "season_code", length = 50, nullable = false)
    private String seasonCode;

    @Column(name = "season_name", length = 150, nullable = false)
    private String seasonName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "prediction_lock_hours")
    private Integer predictionLockHours = 4;

    @Column(name = "match_prediction_lock_hours")
    private Integer matchPredictionLockHours = 1;

    @Column(name = "status", length = 30)
    private String status = "DRAFT";

    @Column(name = "first_match_start_time")
    private LocalDateTime firstMatchStartTime;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    public LeagueSeason() {
    }

    public LeagueSeason(League league, String seasonCode, String seasonName, LocalDate startDate, LocalDate endDate, Integer predictionLockHours, Integer matchPredictionLockHours, String status, LocalDateTime firstMatchStartTime, LocalDateTime closedAt) {
        this.league = league;
        this.seasonCode = seasonCode;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.predictionLockHours = predictionLockHours;
        this.matchPredictionLockHours = matchPredictionLockHours;
        this.status = status;
        this.firstMatchStartTime = firstMatchStartTime;
        this.closedAt = closedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private League league;
        private String seasonCode;
        private String seasonName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer predictionLockHours = 4;
        private Integer matchPredictionLockHours = 1;
        private String status = "DRAFT";
        private LocalDateTime firstMatchStartTime;
        private LocalDateTime closedAt;

        public Builder league(League league) {
            this.league = league;
            return this;
        }

        public Builder seasonCode(String seasonCode) {
            this.seasonCode = seasonCode;
            return this;
        }

        public Builder seasonName(String seasonName) {
            this.seasonName = seasonName;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder predictionLockHours(Integer predictionLockHours) {
            this.predictionLockHours = predictionLockHours;
            return this;
        }

        public Builder matchPredictionLockHours(Integer matchPredictionLockHours) {
            this.matchPredictionLockHours = matchPredictionLockHours;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder firstMatchStartTime(LocalDateTime firstMatchStartTime) {
            this.firstMatchStartTime = firstMatchStartTime;
            return this;
        }

        public Builder closedAt(LocalDateTime closedAt) {
            this.closedAt = closedAt;
            return this;
        }

        public LeagueSeason build() {
            LeagueSeason obj = new LeagueSeason();
            obj.league = this.league;
            obj.seasonCode = this.seasonCode;
            obj.seasonName = this.seasonName;
            obj.startDate = this.startDate;
            obj.endDate = this.endDate;
            obj.predictionLockHours = this.predictionLockHours;
            obj.matchPredictionLockHours = this.matchPredictionLockHours;
            obj.status = this.status;
            obj.firstMatchStartTime = this.firstMatchStartTime;
            obj.closedAt = this.closedAt;
            return obj;
        }
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPredictionLockHours() {
        return predictionLockHours;
    }

    public void setPredictionLockHours(Integer predictionLockHours) {
        this.predictionLockHours = predictionLockHours;
    }

    public Integer getMatchPredictionLockHours() {
        return matchPredictionLockHours;
    }

    public void setMatchPredictionLockHours(Integer matchPredictionLockHours) {
        this.matchPredictionLockHours = matchPredictionLockHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getFirstMatchStartTime() {
        return firstMatchStartTime;
    }

    public void setFirstMatchStartTime(LocalDateTime firstMatchStartTime) {
        this.firstMatchStartTime = firstMatchStartTime;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
}
