package com.familyleague.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_transactions")
@EntityListeners(AuditingEntityListener.class)
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "source_type", length = 30, nullable = false)
    private String sourceType;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(name = "rule_code", length = 50, nullable = false)
    private String ruleCode;

    @Column(name = "points", nullable = false)
    private Integer points;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public PointTransaction() {
    }

    public PointTransaction(Long id, LeagueSeason season, User user, String sourceType, Long sourceId, String ruleCode, Integer points, LocalDateTime createdAt) {
        this.id = id;
        this.season = season;
        this.user = user;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.ruleCode = ruleCode;
        this.points = points;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LeagueSeason season;
        private User user;
        private String sourceType;
        private Long sourceId;
        private String ruleCode;
        private Integer points;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder season(LeagueSeason season) {
            this.season = season;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        public Builder sourceId(Long sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder ruleCode(String ruleCode) {
            this.ruleCode = ruleCode;
            return this;
        }

        public Builder points(Integer points) {
            this.points = points;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PointTransaction build() {
            PointTransaction obj = new PointTransaction();
            obj.id = this.id;
            obj.season = this.season;
            obj.user = this.user;
            obj.sourceType = this.sourceType;
            obj.sourceId = this.sourceId;
            obj.ruleCode = this.ruleCode;
            obj.points = this.points;
            obj.createdAt = this.createdAt;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeagueSeason getSeason() {
        return season;
    }

    public void setSeason(LeagueSeason season) {
        this.season = season;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
