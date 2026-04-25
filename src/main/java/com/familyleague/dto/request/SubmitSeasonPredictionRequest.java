package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Full final-standings prediction — every team in the season must be ranked")
public class SubmitSeasonPredictionRequest {

    @NotEmpty
    @Schema(description = "List of team-rank entries. Each team must appear exactly once, with rank 1 being first place.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TeamRankEntry> rankings;

    public List<TeamRankEntry> getRankings() { return rankings; }
    public void setRankings(List<TeamRankEntry> rankings) { this.rankings = rankings; }

    @Schema(description = "A single team's predicted final position")
    public static class TeamRankEntry {

        @NotNull
        @Schema(description = "Team ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long teamId;

        @NotNull
        @Schema(description = "Predicted final rank (1 = winner)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer rankPosition;

        public Long getTeamId() { return teamId; }
        public void setTeamId(Long teamId) { this.teamId = teamId; }
        public Integer getRankPosition() { return rankPosition; }
        public void setRankPosition(Integer rankPosition) { this.rankPosition = rankPosition; }
    }
}
