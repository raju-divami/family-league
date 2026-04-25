package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scoring_rules")
public class ScoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "points", nullable = false)
    private Integer points;

    public ScoringRule() {
    }

    public ScoringRule(Long id, String code, String description, Integer points) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.points = points;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String description;
        private Integer points;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder points(Integer points) {
            this.points = points;
            return this;
        }

        public ScoringRule build() {
            ScoringRule obj = new ScoringRule();
            obj.id = this.id;
            obj.code = this.code;
            obj.description = this.description;
            obj.points = this.points;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
