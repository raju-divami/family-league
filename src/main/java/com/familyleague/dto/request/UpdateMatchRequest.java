package com.familyleague.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Request DTO for updating an existing match.
 * All fields are optional - only provided fields will be updated.
 * PredictionLockTime will be automatically recalculated if startTime is updated.
 */
@Getter
@Setter
public class UpdateMatchRequest {

    private Integer matchNo;

    private String stage;

    private Long homeTeamId;

    private Long awayTeamId;

    private String venue;

    private LocalDateTime startTime;
}
