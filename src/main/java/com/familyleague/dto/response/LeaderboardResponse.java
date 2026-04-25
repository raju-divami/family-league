package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class LeaderboardResponse {

    private Long id;
    private Long seasonId;
    private Long userId;
    private String userDisplayName;
    private Integer totalPoints;
    private Integer rankNo;
    private LocalDateTime recalculatedAt;

    private LeaderboardResponse(Long id, Long seasonId, Long userId, String userDisplayName,
                                Integer totalPoints, Integer rankNo, LocalDateTime recalculatedAt) {
        this.id = id;
        this.seasonId = seasonId;
        this.userId = userId;
        this.userDisplayName = userDisplayName;
        this.totalPoints = totalPoints;
        this.rankNo = rankNo;
        this.recalculatedAt = recalculatedAt;
    }

    public Long getId() { return id; }
    public Long getSeasonId() { return seasonId; }
    public Long getUserId() { return userId; }
    public String getUserDisplayName() { return userDisplayName; }
    public Integer getTotalPoints() { return totalPoints; }
    public Integer getRankNo() { return rankNo; }
    public LocalDateTime getRecalculatedAt() { return recalculatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long seasonId;
        private Long userId;
        private String userDisplayName;
        private Integer totalPoints;
        private Integer rankNo;
        private LocalDateTime recalculatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder seasonId(Long seasonId) { this.seasonId = seasonId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder userDisplayName(String userDisplayName) { this.userDisplayName = userDisplayName; return this; }
        public Builder totalPoints(Integer totalPoints) { this.totalPoints = totalPoints; return this; }
        public Builder rankNo(Integer rankNo) { this.rankNo = rankNo; return this; }
        public Builder recalculatedAt(LocalDateTime recalculatedAt) { this.recalculatedAt = recalculatedAt; return this; }

        public LeaderboardResponse build() {
            return new LeaderboardResponse(id, seasonId, userId, userDisplayName, totalPoints, rankNo, recalculatedAt);
        }
    }
}
