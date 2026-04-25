package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AuditLogResponse {

    private Long id;
    private String tableName;
    private Long recordId;
    private String action;
    private String oldData;
    private String newData;
    private Long changedBy;
    private LocalDateTime changedAt;
}
