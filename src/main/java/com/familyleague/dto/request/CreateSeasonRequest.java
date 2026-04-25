package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Payload for creating a season under a league")
public class CreateSeasonRequest {

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Unique season code within the league", example = "IPL2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String seasonCode;

    @NotBlank
    @Size(max = 150)
    @Schema(description = "Season display name", example = "IPL 2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String seasonName;

    @NotNull
    @Schema(description = "Season start date", example = "2026-03-20", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate startDate;

    @NotNull
    @Schema(description = "Season end date", example = "2026-05-30", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate endDate;

    @Schema(description = "Hours before first match when season predictions lock", example = "4", defaultValue = "4")
    private Integer predictionLockHours = 4;

    @Schema(description = "Hours before each match when match predictions lock", example = "1", defaultValue = "1")
    private Integer matchPredictionLockHours = 1;
}
