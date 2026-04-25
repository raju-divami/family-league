package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

    public String getSeasonCode() { return seasonCode; }
    public void setSeasonCode(String seasonCode) { this.seasonCode = seasonCode; }
    public String getSeasonName() { return seasonName; }
    public void setSeasonName(String seasonName) { this.seasonName = seasonName; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getPredictionLockHours() { return predictionLockHours; }
    public void setPredictionLockHours(Integer predictionLockHours) { this.predictionLockHours = predictionLockHours; }
    public Integer getMatchPredictionLockHours() { return matchPredictionLockHours; }
    public void setMatchPredictionLockHours(Integer matchPredictionLockHours) { this.matchPredictionLockHours = matchPredictionLockHours; }
}
