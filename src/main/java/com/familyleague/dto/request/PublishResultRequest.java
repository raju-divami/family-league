package com.familyleague.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishResultRequest {

    private Long winnerTeamId;

    private Long tossWinnerTeamId;

    private Long playerOfMatchId;

    private Boolean isTie;

    private String remarks;
}
