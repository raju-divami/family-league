package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "team_players",
       uniqueConstraints = @UniqueConstraint(columnNames = {"season_team_id", "player_id"}))
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_team_id", nullable = false)
    private SeasonTeam seasonTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public TeamPlayer() {
    }

    public TeamPlayer(Long id, SeasonTeam seasonTeam, Player player) {
        this.id = id;
        this.seasonTeam = seasonTeam;
        this.player = player;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private SeasonTeam seasonTeam;
        private Player player;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder seasonTeam(SeasonTeam seasonTeam) {
            this.seasonTeam = seasonTeam;
            return this;
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public TeamPlayer build() {
            TeamPlayer obj = new TeamPlayer();
            obj.id = this.id;
            obj.seasonTeam = this.seasonTeam;
            obj.player = this.player;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeasonTeam getSeasonTeam() {
        return seasonTeam;
    }

    public void setSeasonTeam(SeasonTeam seasonTeam) {
        this.seasonTeam = seasonTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
