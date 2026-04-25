package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_results")
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", unique = true, nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toss_winner_team_id")
    private Team tossWinnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_of_match_id")
    private Player playerOfMatch;

    @Column(name = "is_tie")
    private boolean tie = false;

    @Column(name = "remarks", columnDefinition = "text")
    private String remarks;

    @Column(name = "published_by")
    private Long publishedBy;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    public MatchResult() {
    }

    public MatchResult(Long id, Match match, Team tossWinnerTeam, Team winnerTeam, Player playerOfMatch, boolean tie, String remarks, Long publishedBy, LocalDateTime publishedAt) {
        this.id = id;
        this.match = match;
        this.tossWinnerTeam = tossWinnerTeam;
        this.winnerTeam = winnerTeam;
        this.playerOfMatch = playerOfMatch;
        this.tie = tie;
        this.remarks = remarks;
        this.publishedBy = publishedBy;
        this.publishedAt = publishedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Match match;
        private Team tossWinnerTeam;
        private Team winnerTeam;
        private Player playerOfMatch;
        private boolean tie = false;
        private String remarks;
        private Long publishedBy;
        private LocalDateTime publishedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder match(Match match) {
            this.match = match;
            return this;
        }

        public Builder tossWinnerTeam(Team tossWinnerTeam) {
            this.tossWinnerTeam = tossWinnerTeam;
            return this;
        }

        public Builder winnerTeam(Team winnerTeam) {
            this.winnerTeam = winnerTeam;
            return this;
        }

        public Builder playerOfMatch(Player playerOfMatch) {
            this.playerOfMatch = playerOfMatch;
            return this;
        }

        public Builder tie(boolean tie) {
            this.tie = tie;
            return this;
        }

        public Builder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        public Builder publishedBy(Long publishedBy) {
            this.publishedBy = publishedBy;
            return this;
        }

        public Builder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public MatchResult build() {
            MatchResult obj = new MatchResult();
            obj.id = this.id;
            obj.match = this.match;
            obj.tossWinnerTeam = this.tossWinnerTeam;
            obj.winnerTeam = this.winnerTeam;
            obj.playerOfMatch = this.playerOfMatch;
            obj.tie = this.tie;
            obj.remarks = this.remarks;
            obj.publishedBy = this.publishedBy;
            obj.publishedAt = this.publishedAt;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTossWinnerTeam() {
        return tossWinnerTeam;
    }

    public void setTossWinnerTeam(Team tossWinnerTeam) {
        this.tossWinnerTeam = tossWinnerTeam;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public Player getPlayerOfMatch() {
        return playerOfMatch;
    }

    public void setPlayerOfMatch(Player playerOfMatch) {
        this.playerOfMatch = playerOfMatch;
    }

    public boolean isTie() {
        return tie;
    }

    public void setTie(boolean tie) {
        this.tie = tie;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(Long publishedBy) {
        this.publishedBy = publishedBy;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
