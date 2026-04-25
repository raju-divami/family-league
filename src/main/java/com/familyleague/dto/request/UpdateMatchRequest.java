package com.familyleague.dto.request;

import java.time.LocalDateTime;

public class UpdateMatchRequest {

    private Integer matchNo;

    private String stage;

    private Long homeTeamId;

    private Long awayTeamId;

    private String venue;

    private LocalDateTime startTime;

    public Integer getMatchNo() { return matchNo; }
    public void setMatchNo(Integer matchNo) { this.matchNo = matchNo; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public Long getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }
    public Long getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
}
