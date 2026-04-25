package com.familyleague.service;

import com.familyleague.dto.response.AuditLogResponse;
import com.familyleague.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    void log(String tableName, Long recordId, String action, String oldData, String newData, Long changedBy);

    PagedResponse<AuditLogResponse> getLogsForRecord(String tableName, Long recordId, Pageable pageable);

    PagedResponse<AuditLogResponse> getLogsByUser(Long userId, Pageable pageable);
}
