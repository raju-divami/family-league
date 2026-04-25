package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class MatchResultResponse {

    private Long id;
    private Long matchId;
    private Long tossWinnerTeamId;
    private String tossWinnerTeamName;
    private Long winnerTeamId;
    private String winnerTeamName;
    private Long playerOfMatchId;
    private String playerOfMatchName;
    private boolean tie;
    private String remarks;
    private LocalDateTime publishedAt;

    private MatchResultResponse(Long id, Long matchId, Long tossWinnerTeamId, String tossWinnerTeamName,
                                Long winnerTeamId, String winnerTeamName, Long playerOfMatchId,
                                String playerOfMatchName, boolean tie, String remarks, LocalDateTime publishedAt) {
        this.id = id;
        this.matchId = matchId;
        this.tossWinnerTeamId = tossWinnerTeamId;
        this.tossWinnerTeamName = tossWinnerTeamName;
        this.winnerTeamId = winnerTeamId;
        this.winnerTeamName = winnerTeamName;
        this.playerOfMatchId = playerOfMatchId;
        this.playerOfMatchName = playerOfMatchName;
        this.tie = tie;
        this.remarks = remarks;
        this.publishedAt = publishedAt;
    }

    public Long getId() { return id; }
    public Long getMatchId() { return matchId; }
    public Long getTossWinnerTeamId() { return tossWinnerTeamId; }
    public String getTossWinnerTeamName() { return tossWinnerTeamName; }
    public Long getWinnerTeamId() { return winnerTeamId; }
    public String getWinnerTeamName() { return winnerTeamName; }
    public Long getPlayerOfMatchId() { return playerOfMatchId; }
    public String getPlayerOfMatchName() { return playerOfMatchName; }
    public boolean isTie() { return tie; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getPublishedAt() { return publishedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long matchId;
        private Long tossWinnerTeamId;
        private String tossWinnerTeamName;
        private Long winnerTeamId;
        private String winnerTeamName;
        private Long playerOfMatchId;
        private String playerOfMatchName;
        private boolean tie;
        private String remarks;
        private LocalDateTime publishedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder matchId(Long matchId) { this.matchId = matchId; return this; }
        public Builder tossWinnerTeamId(Long tossWinnerTeamId) { this.tossWinnerTeamId = tossWinnerTeamId; return this; }
        public Builder tossWinnerTeamName(String tossWinnerTeamName) { this.tossWinnerTeamName = tossWinnerTeamName; return this; }
        public Builder winnerTeamId(Long winnerTeamId) { this.winnerTeamId = winnerTeamId; return this; }
        public Builder winnerTeamName(String winnerTeamName) { this.winnerTeamName = winnerTeamName; return this; }
        public Builder playerOfMatchId(Long playerOfMatchId) { this.playerOfMatchId = playerOfMatchId; return this; }
        public Builder playerOfMatchName(String playerOfMatchName) { this.playerOfMatchName = playerOfMatchName; return this; }
        public Builder tie(boolean tie) { this.tie = tie; return this; }
        public Builder remarks(String remarks) { this.remarks = remarks; return this; }
        public Builder publishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; return this; }

        public MatchResultResponse build() {
            return new MatchResultResponse(id, matchId, tossWinnerTeamId, tossWinnerTeamName,
                    winnerTeamId, winnerTeamName, playerOfMatchId, playerOfMatchName,
                    tie, remarks, publishedAt);
        }
    }
}
