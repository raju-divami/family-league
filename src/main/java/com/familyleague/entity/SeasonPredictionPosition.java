package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "season_prediction_positions",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"prediction_id", "rank_position"}),
           @UniqueConstraint(columnNames = {"prediction_id", "team_id"})
       })
public class SeasonPredictionPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prediction_id", nullable = false)
    private SeasonPrediction prediction;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public SeasonPredictionPosition() {
    }

    public SeasonPredictionPosition(Long id, SeasonPrediction prediction, Integer rankPosition, Team team) {
        this.id = id;
        this.prediction = prediction;
        this.rankPosition = rankPosition;
        this.team = team;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private SeasonPrediction prediction;
        private Integer rankPosition;
        private Team team;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder prediction(SeasonPrediction prediction) {
            this.prediction = prediction;
            return this;
        }

        public Builder rankPosition(Integer rankPosition) {
            this.rankPosition = rankPosition;
            return this;
        }

        public Builder team(Team team) {
            this.team = team;
            return this;
        }

        public SeasonPredictionPosition build() {
            SeasonPredictionPosition obj = new SeasonPredictionPosition();
            obj.id = this.id;
            obj.prediction = this.prediction;
            obj.rankPosition = this.rankPosition;
            obj.team = this.team;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeasonPrediction getPrediction() {
        return prediction;
    }

    public void setPrediction(SeasonPrediction prediction) {
        this.prediction = prediction;
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
