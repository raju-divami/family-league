package com.familyleague.repository;

import com.familyleague.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findByTableNameAndRecordId(String tableName, Long recordId, Pageable pageable);

    Page<AuditLog> findByChangedBy(Long changedBy, Pageable pageable);
}
