package com.familyleague.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeagueSeasonResponse {

    private Long id;
    private Long leagueId;
    private String leagueName;
    private String seasonCode;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer predictionLockHours;
    private Integer matchPredictionLockHours;
    private String status;
    private LocalDateTime firstMatchStartTime;
    private LocalDateTime closedAt;

    private LeagueSeasonResponse(Long id, Long leagueId, String leagueName, String seasonCode,
                                 String seasonName, LocalDate startDate, LocalDate endDate,
                                 Integer predictionLockHours, Integer matchPredictionLockHours,
                                 String status, LocalDateTime firstMatchStartTime, LocalDateTime closedAt) {
        this.id = id;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
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

    public Long getId() { return id; }
    public Long getLeagueId() { return leagueId; }
    public String getLeagueName() { return leagueName; }
    public String getSeasonCode() { return seasonCode; }
    public String getSeasonName() { return seasonName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Integer getPredictionLockHours() { return predictionLockHours; }
    public Integer getMatchPredictionLockHours() { return matchPredictionLockHours; }
    public String getStatus() { return status; }
    public LocalDateTime getFirstMatchStartTime() { return firstMatchStartTime; }
    public LocalDateTime getClosedAt() { return closedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long leagueId;
        private String leagueName;
        private String seasonCode;
        private String seasonName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer predictionLockHours;
        private Integer matchPredictionLockHours;
        private String status;
        private LocalDateTime firstMatchStartTime;
        private LocalDateTime closedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder leagueId(Long leagueId) { this.leagueId = leagueId; return this; }
        public Builder leagueName(String leagueName) { this.leagueName = leagueName; return this; }
        public Builder seasonCode(String seasonCode) { this.seasonCode = seasonCode; return this; }
        public Builder seasonName(String seasonName) { this.seasonName = seasonName; return this; }
        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder predictionLockHours(Integer predictionLockHours) { this.predictionLockHours = predictionLockHours; return this; }
        public Builder matchPredictionLockHours(Integer matchPredictionLockHours) { this.matchPredictionLockHours = matchPredictionLockHours; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder firstMatchStartTime(LocalDateTime firstMatchStartTime) { this.firstMatchStartTime = firstMatchStartTime; return this; }
        public Builder closedAt(LocalDateTime closedAt) { this.closedAt = closedAt; return this; }

        public LeagueSeasonResponse build() {
            return new LeagueSeasonResponse(id, leagueId, leagueName, seasonCode, seasonName,
                    startDate, endDate, predictionLockHours, matchPredictionLockHours,
                    status, firstMatchStartTime, closedAt);
        }
    }
}
