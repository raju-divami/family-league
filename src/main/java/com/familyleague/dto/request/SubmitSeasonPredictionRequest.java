package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Full final-standings prediction — every team in the season must be ranked")
public class SubmitSeasonPredictionRequest {

    @NotEmpty
    @Schema(description = "List of team-rank entries. Each team must appear exactly once, with rank 1 being first place.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TeamRankEntry> rankings;

    @Getter
    @Setter
    @Schema(description = "A single team's predicted final position")
    public static class TeamRankEntry {

        @NotNull
        @Schema(description = "Team ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long teamId;

        @NotNull
        @Schema(description = "Predicted final rank (1 = winner)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer rankPosition;
    }
}
