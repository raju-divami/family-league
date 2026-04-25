package com.familyleague.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitMatchPredictionRequest {

    private Long predictedWinnerTeamId;

    private Long predictedTossTeamId;

    private Long predictedPlayerId;
}
