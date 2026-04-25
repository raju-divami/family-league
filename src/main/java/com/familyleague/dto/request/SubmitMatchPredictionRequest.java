package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Prediction for a single match — at least one field should be provided")
public class SubmitMatchPredictionRequest {

    @Schema(description = "ID of the team predicted to win the match", example = "1")
    private Long predictedWinnerTeamId;

    @Schema(description = "ID of the team predicted to win the toss", example = "2")
    private Long predictedTossTeamId;

    @Schema(description = "ID of the player predicted as Player of the Match", example = "7")
    private Long predictedPlayerId;

    public Long getPredictedWinnerTeamId() { return predictedWinnerTeamId; }
    public void setPredictedWinnerTeamId(Long predictedWinnerTeamId) { this.predictedWinnerTeamId = predictedWinnerTeamId; }
    public Long getPredictedTossTeamId() { return predictedTossTeamId; }
    public void setPredictedTossTeamId(Long predictedTossTeamId) { this.predictedTossTeamId = predictedTossTeamId; }
    public Long getPredictedPlayerId() { return predictedPlayerId; }
    public void setPredictedPlayerId(Long predictedPlayerId) { this.predictedPlayerId = predictedPlayerId; }
}
