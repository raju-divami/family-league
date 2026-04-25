package com.familyleague.dto.request;

import jakarta.validation.constraints.NotNull;

public class AddTeamToSeasonRequest {

    @NotNull
    private Long teamId;

    private Integer seedRank;

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public Integer getSeedRank() { return seedRank; }
    public void setSeedRank(Integer seedRank) { this.seedRank = seedRank; }
}
