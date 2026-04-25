package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchPredictionResponse {

    private Long id;
    private Long matchId;
    private Long userId;
    private Long predictedWinnerTeamId;
    private String predictedWinnerTeamName;
    private Long predictedTossTeamId;
    private String predictedTossTeamName;
    private Long predictedPlayerId;
    private String predictedPlayerName;
    private String status;
    private LocalDateTime submittedAt;
}
