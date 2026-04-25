package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class AuditLogResponse {

    private Long id;
    private String tableName;
    private Long recordId;
    private String action;
    private String oldData;
    private String newData;
    private Long changedBy;
    private LocalDateTime changedAt;

    private AuditLogResponse(Long id, String tableName, Long recordId, String action,
                             String oldData, String newData, Long changedBy, LocalDateTime changedAt) {
        this.id = id;
        this.tableName = tableName;
        this.recordId = recordId;
        this.action = action;
        this.oldData = oldData;
        this.newData = newData;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
    }

    public Long getId() { return id; }
    public String getTableName() { return tableName; }
    public Long getRecordId() { return recordId; }
    public String getAction() { return action; }
    public String getOldData() { return oldData; }
    public String getNewData() { return newData; }
    public Long getChangedBy() { return changedBy; }
    public LocalDateTime getChangedAt() { return changedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String tableName;
        private Long recordId;
        private String action;
        private String oldData;
        private String newData;
        private Long changedBy;
        private LocalDateTime changedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder tableName(String tableName) { this.tableName = tableName; return this; }
        public Builder recordId(Long recordId) { this.recordId = recordId; return this; }
        public Builder action(String action) { this.action = action; return this; }
        public Builder oldData(String oldData) { this.oldData = oldData; return this; }
        public Builder newData(String newData) { this.newData = newData; return this; }
        public Builder changedBy(Long changedBy) { this.changedBy = changedBy; return this; }
        public Builder changedAt(LocalDateTime changedAt) { this.changedAt = changedAt; return this; }

        public AuditLogResponse build() {
            return new AuditLogResponse(id, tableName, recordId, action, oldData, newData, changedBy, changedAt);
        }
    }
}
