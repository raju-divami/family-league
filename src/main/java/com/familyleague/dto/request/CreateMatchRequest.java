package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Payload for scheduling a match")
public class CreateMatchRequest {

    @NotNull
    @Schema(description = "Season the match belongs to", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long seasonId;

    @Schema(description = "Sequential match number within the season", example = "1")
    private Integer matchNo;

    @Schema(description = "Stage of the match", example = "LEAGUE",
            allowableValues = {"LEAGUE", "QUALIFIER", "ELIMINATOR", "SEMI_FINAL", "FINAL"}, defaultValue = "LEAGUE")
    private String stage = "LEAGUE";

    @NotNull
    @Schema(description = "Home team ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long homeTeamId;

    @NotNull
    @Schema(description = "Away team ID", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long awayTeamId;

    @Schema(description = "Venue name", example = "Wankhede Stadium, Mumbai")
    private String venue;

    @NotNull
    @Schema(description = "Match start date-time (ISO 8601)", example = "2026-03-21T19:30:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;
}
