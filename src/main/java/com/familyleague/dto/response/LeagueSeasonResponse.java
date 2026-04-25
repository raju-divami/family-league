package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class LeagueSeasonResponse {

    private Long id;
    private Long leagueId;
    private String leagueName;
    private String seasonCode;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer predictionLockHours;
    private Integer matchPredictionLockHours;
    private String status;
    private LocalDateTime firstMatchStartTime;
    private LocalDateTime closedAt;
}
