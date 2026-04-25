package com.familyleague.service.impl;

import com.familyleague.dto.response.AuditLogResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.entity.AuditLog;
import com.familyleague.mapper.AuditMapper;
import com.familyleague.repository.AuditLogRepository;
import com.familyleague.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);

    private final AuditLogRepository auditLogRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditLogRepository auditLogRepository, AuditMapper auditMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditMapper = auditMapper;
    }

    @Override
    @Transactional
    public void log(String tableName, Long recordId, String action, String oldData, String newData, Long changedBy) {
        log.debug("Logging audit entry for table: {}, recordId: {}, action: {}", tableName, recordId, action);

        AuditLog auditLog = new AuditLog();
        auditLog.setTableName(tableName);
        auditLog.setRecordId(recordId);
        auditLog.setAction(action);
        auditLog.setOldData(oldData);
        auditLog.setNewData(newData);
        auditLog.setChangedBy(changedBy);

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AuditLogResponse> getLogsForRecord(String tableName, Long recordId, Pageable pageable) {
        Page<AuditLog> page = auditLogRepository.findByTableNameAndRecordId(tableName, recordId, pageable);
        return PagedResponse.<AuditLogResponse>builder()
                .content(page.getContent().stream().map(auditMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AuditLogResponse> getLogsByUser(Long userId, Pageable pageable) {
        Page<AuditLog> page = auditLogRepository.findByChangedBy(userId, pageable);
        return PagedResponse.<AuditLogResponse>builder()
                .content(page.getContent().stream().map(auditMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
