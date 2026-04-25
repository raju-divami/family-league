package com.familyleague.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "action", length = 20)
    private String action;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_data", columnDefinition = "json")
    private String oldData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_data", columnDefinition = "json")
    private String newData;

    @Column(name = "changed_by")
    private Long changedBy;

    @CreatedDate
    @Column(name = "changed_at", updatable = false)
    private LocalDateTime changedAt;

    public AuditLog() {
    }

    public AuditLog(Long id, String tableName, Long recordId, String action, String oldData, String newData, Long changedBy, LocalDateTime changedAt) {
        this.id = id;
        this.tableName = tableName;
        this.recordId = recordId;
        this.action = action;
        this.oldData = oldData;
        this.newData = newData;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String tableName;
        private Long recordId;
        private String action;
        private String oldData;
        private String newData;
        private Long changedBy;
        private LocalDateTime changedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder recordId(Long recordId) {
            this.recordId = recordId;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder oldData(String oldData) {
            this.oldData = oldData;
            return this;
        }

        public Builder newData(String newData) {
            this.newData = newData;
            return this;
        }

        public Builder changedBy(Long changedBy) {
            this.changedBy = changedBy;
            return this;
        }

        public Builder changedAt(LocalDateTime changedAt) {
            this.changedAt = changedAt;
            return this;
        }

        public AuditLog build() {
            AuditLog obj = new AuditLog();
            obj.id = this.id;
            obj.tableName = this.tableName;
            obj.recordId = this.recordId;
            obj.action = this.action;
            obj.oldData = this.oldData;
            obj.newData = this.newData;
            obj.changedBy = this.changedBy;
            obj.changedAt = this.changedAt;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
