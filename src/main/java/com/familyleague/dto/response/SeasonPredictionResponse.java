package com.familyleague.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class SeasonPredictionResponse {

    private Long id;
    private Long seasonId;
    private Long userId;
    private String status;
    private LocalDateTime submittedAt;
    private List<TeamRankEntry> rankings;

    private SeasonPredictionResponse(Long id, Long seasonId, Long userId, String status,
                                     LocalDateTime submittedAt, List<TeamRankEntry> rankings) {
        this.id = id;
        this.seasonId = seasonId;
        this.userId = userId;
        this.status = status;
        this.submittedAt = submittedAt;
        this.rankings = rankings;
    }

    public Long getId() { return id; }
    public Long getSeasonId() { return seasonId; }
    public Long getUserId() { return userId; }
    public String getStatus() { return status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public List<TeamRankEntry> getRankings() { return rankings; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long seasonId;
        private Long userId;
        private String status;
        private LocalDateTime submittedAt;
        private List<TeamRankEntry> rankings;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder seasonId(Long seasonId) { this.seasonId = seasonId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }
        public Builder rankings(List<TeamRankEntry> rankings) { this.rankings = rankings; return this; }

        public SeasonPredictionResponse build() {
            return new SeasonPredictionResponse(id, seasonId, userId, status, submittedAt, rankings);
        }
    }

    public static class TeamRankEntry {
        private Integer rankPosition;
        private Long teamId;
        private String teamName;

        private TeamRankEntry(Integer rankPosition, Long teamId, String teamName) {
            this.rankPosition = rankPosition;
            this.teamId = teamId;
            this.teamName = teamName;
        }

        public Integer getRankPosition() { return rankPosition; }
        public Long getTeamId() { return teamId; }
        public String getTeamName() { return teamName; }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Integer rankPosition;
            private Long teamId;
            private String teamName;

            public Builder rankPosition(Integer rankPosition) { this.rankPosition = rankPosition; return this; }
            public Builder teamId(Long teamId) { this.teamId = teamId; return this; }
            public Builder teamName(String teamName) { this.teamName = teamName; return this; }

            public TeamRankEntry build() { return new TeamRankEntry(rankPosition, teamId, teamName); }
        }
    }
}
