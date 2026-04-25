package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointTransactionResponse {

    private Long id;
    private String sourceType;
    private Long sourceId;
    private String ruleCode;
    private Integer points;
    private LocalDateTime createdAt;
}
