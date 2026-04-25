package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class MatchResponse {

    private Long id;
    private Long seasonId;
    private Integer matchNo;
    private String stage;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime predictionLockTime;
    private String status;

    private MatchResponse(Long id, Long seasonId, Integer matchNo, String stage,
                          Long homeTeamId, String homeTeamName, Long awayTeamId,
                          String awayTeamName, String venue, LocalDateTime startTime,
                          LocalDateTime predictionLockTime, String status) {
        this.id = id;
        this.seasonId = seasonId;
        this.matchNo = matchNo;
        this.stage = stage;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.venue = venue;
        this.startTime = startTime;
        this.predictionLockTime = predictionLockTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getSeasonId() { return seasonId; }
    public Integer getMatchNo() { return matchNo; }
    public String getStage() { return stage; }
    public Long getHomeTeamId() { return homeTeamId; }
    public String getHomeTeamName() { return homeTeamName; }
    public Long getAwayTeamId() { return awayTeamId; }
    public String getAwayTeamName() { return awayTeamName; }
    public String getVenue() { return venue; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getPredictionLockTime() { return predictionLockTime; }
    public String getStatus() { return status; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long seasonId;
        private Integer matchNo;
        private String stage;
        private Long homeTeamId;
        private String homeTeamName;
        private Long awayTeamId;
        private String awayTeamName;
        private String venue;
        private LocalDateTime startTime;
        private LocalDateTime predictionLockTime;
        private String status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder seasonId(Long seasonId) { this.seasonId = seasonId; return this; }
        public Builder matchNo(Integer matchNo) { this.matchNo = matchNo; return this; }
        public Builder stage(String stage) { this.stage = stage; return this; }
        public Builder homeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; return this; }
        public Builder homeTeamName(String homeTeamName) { this.homeTeamName = homeTeamName; return this; }
        public Builder awayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; return this; }
        public Builder awayTeamName(String awayTeamName) { this.awayTeamName = awayTeamName; return this; }
        public Builder venue(String venue) { this.venue = venue; return this; }
        public Builder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public Builder predictionLockTime(LocalDateTime predictionLockTime) { this.predictionLockTime = predictionLockTime; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public MatchResponse build() {
            return new MatchResponse(id, seasonId, matchNo, stage, homeTeamId, homeTeamName,
                    awayTeamId, awayTeamName, venue, startTime, predictionLockTime, status);
        }
    }
}
