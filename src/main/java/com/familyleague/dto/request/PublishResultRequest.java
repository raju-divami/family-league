package com.familyleague.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishResultRequest {

    private Long tossWinnerTeamId;

    private Long winnerTeamId;

    private Long playerOfMatchId;

    private boolean tie = false;

    private String remarks;
}
