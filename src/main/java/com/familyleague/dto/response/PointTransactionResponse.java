package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class PointTransactionResponse {

    private Long id;
    private String sourceType;
    private Long sourceId;
    private String ruleCode;
    private Integer points;
    private LocalDateTime createdAt;

    private PointTransactionResponse(Long id, String sourceType, Long sourceId, String ruleCode,
                                     Integer points, LocalDateTime createdAt) {
        this.id = id;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.ruleCode = ruleCode;
        this.points = points;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getSourceType() { return sourceType; }
    public Long getSourceId() { return sourceId; }
    public String getRuleCode() { return ruleCode; }
    public Integer getPoints() { return points; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String sourceType;
        private Long sourceId;
        private String ruleCode;
        private Integer points;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder sourceType(String sourceType) { this.sourceType = sourceType; return this; }
        public Builder sourceId(Long sourceId) { this.sourceId = sourceId; return this; }
        public Builder ruleCode(String ruleCode) { this.ruleCode = ruleCode; return this; }
        public Builder points(Integer points) { this.points = points; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public PointTransactionResponse build() {
            return new PointTransactionResponse(id, sourceType, sourceId, ruleCode, points, createdAt);
        }
    }
}
