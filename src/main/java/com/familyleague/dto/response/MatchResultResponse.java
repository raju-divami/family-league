package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchResultResponse {

    private Long id;
    private Long matchId;
    private Long tossWinnerTeamId;
    private String tossWinnerTeamName;
    private Long winnerTeamId;
    private String winnerTeamName;
    private Long playerOfMatchId;
    private String playerOfMatchName;
    private boolean tie;
    private String remarks;
    private LocalDateTime publishedAt;
}
