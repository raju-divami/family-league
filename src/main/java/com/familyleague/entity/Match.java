package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @Column(name = "match_no")
    private Integer matchNo;

    @Column(name = "stage", length = 50)
    private String stage = "LEAGUE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @Column(name = "venue", length = 150)
    private String venue;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "prediction_lock_time", nullable = false)
    private LocalDateTime predictionLockTime;

    @Column(name = "status", length = 30)
    private String status = "SCHEDULED";

    public Match() {
    }

    public Match(LeagueSeason season, Integer matchNo, String stage, Team homeTeam, Team awayTeam, String venue, LocalDateTime startTime, LocalDateTime predictionLockTime, String status) {
        this.season = season;
        this.matchNo = matchNo;
        this.stage = stage;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = venue;
        this.startTime = startTime;
        this.predictionLockTime = predictionLockTime;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LeagueSeason season;
        private Integer matchNo;
        private String stage = "LEAGUE";
        private Team homeTeam;
        private Team awayTeam;
        private String venue;
        private LocalDateTime startTime;
        private LocalDateTime predictionLockTime;
        private String status = "SCHEDULED";

        public Builder season(LeagueSeason season) {
            this.season = season;
            return this;
        }

        public Builder matchNo(Integer matchNo) {
            this.matchNo = matchNo;
            return this;
        }

        public Builder stage(String stage) {
            this.stage = stage;
            return this;
        }

        public Builder homeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder awayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Builder venue(String venue) {
            this.venue = venue;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder predictionLockTime(LocalDateTime predictionLockTime) {
            this.predictionLockTime = predictionLockTime;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Match build() {
            Match obj = new Match();
            obj.season = this.season;
            obj.matchNo = this.matchNo;
            obj.stage = this.stage;
            obj.homeTeam = this.homeTeam;
            obj.awayTeam = this.awayTeam;
            obj.venue = this.venue;
            obj.startTime = this.startTime;
            obj.predictionLockTime = this.predictionLockTime;
            obj.status = this.status;
            return obj;
        }
    }

    public LeagueSeason getSeason() {
        return season;
    }

    public void setSeason(LeagueSeason season) {
        this.season = season;
    }

    public Integer getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(Integer matchNo) {
        this.matchNo = matchNo;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getPredictionLockTime() {
        return predictionLockTime;
    }

    public void setPredictionLockTime(LocalDateTime predictionLockTime) {
        this.predictionLockTime = predictionLockTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
