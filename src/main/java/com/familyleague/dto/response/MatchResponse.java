package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchResponse {

    private Long id;
    private Long seasonId;
    private Integer matchNo;
    private String stage;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime predictionLockTime;
    private String status;
}
