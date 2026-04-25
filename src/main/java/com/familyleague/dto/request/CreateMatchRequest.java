package com.familyleague.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateMatchRequest {

    @NotNull
    private Long seasonId;

    private Integer matchNo;

    private String stage = "LEAGUE";

    @NotNull
    private Long homeTeamId;

    @NotNull
    private Long awayTeamId;

    private String venue;

    @NotNull
    private LocalDateTime startTime;
}
