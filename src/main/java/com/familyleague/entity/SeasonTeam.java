package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "season_teams",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_id", "team_id"}))
public class SeasonTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private LeagueSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "seed_rank")
    private Integer seedRank;

    public SeasonTeam() {
    }

    public SeasonTeam(Long id, LeagueSeason season, Team team, Integer seedRank) {
        this.id = id;
        this.season = season;
        this.team = team;
        this.seedRank = seedRank;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private LeagueSeason season;
        private Team team;
        private Integer seedRank;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder season(LeagueSeason season) {
            this.season = season;
            return this;
        }

        public Builder team(Team team) {
            this.team = team;
            return this;
        }

        public Builder seedRank(Integer seedRank) {
            this.seedRank = seedRank;
            return this;
        }

        public SeasonTeam build() {
            SeasonTeam obj = new SeasonTeam();
            obj.id = this.id;
            obj.season = this.season;
            obj.team = this.team;
            obj.seedRank = this.seedRank;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getSeedRank() {
        return seedRank;
    }

    public void setSeedRank(Integer seedRank) {
        this.seedRank = seedRank;
    }
}
