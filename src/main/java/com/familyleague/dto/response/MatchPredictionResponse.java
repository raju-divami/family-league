package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class MatchPredictionResponse {

    private Long id;
    private Long matchId;
    private Long userId;
    private Long predictedWinnerTeamId;
    private String predictedWinnerTeamName;
    private Long predictedTossTeamId;
    private String predictedTossTeamName;
    private Long predictedPlayerId;
    private String predictedPlayerName;
    private String status;
    private LocalDateTime submittedAt;

    private MatchPredictionResponse(Long id, Long matchId, Long userId, Long predictedWinnerTeamId,
                                    String predictedWinnerTeamName, Long predictedTossTeamId,
                                    String predictedTossTeamName, Long predictedPlayerId,
                                    String predictedPlayerName, String status, LocalDateTime submittedAt) {
        this.id = id;
        this.matchId = matchId;
        this.userId = userId;
        this.predictedWinnerTeamId = predictedWinnerTeamId;
        this.predictedWinnerTeamName = predictedWinnerTeamName;
        this.predictedTossTeamId = predictedTossTeamId;
        this.predictedTossTeamName = predictedTossTeamName;
        this.predictedPlayerId = predictedPlayerId;
        this.predictedPlayerName = predictedPlayerName;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    public Long getId() { return id; }
    public Long getMatchId() { return matchId; }
    public Long getUserId() { return userId; }
    public Long getPredictedWinnerTeamId() { return predictedWinnerTeamId; }
    public String getPredictedWinnerTeamName() { return predictedWinnerTeamName; }
    public Long getPredictedTossTeamId() { return predictedTossTeamId; }
    public String getPredictedTossTeamName() { return predictedTossTeamName; }
    public Long getPredictedPlayerId() { return predictedPlayerId; }
    public String getPredictedPlayerName() { return predictedPlayerName; }
    public String getStatus() { return status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long matchId;
        private Long userId;
        private Long predictedWinnerTeamId;
        private String predictedWinnerTeamName;
        private Long predictedTossTeamId;
        private String predictedTossTeamName;
        private Long predictedPlayerId;
        private String predictedPlayerName;
        private String status;
        private LocalDateTime submittedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder matchId(Long matchId) { this.matchId = matchId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder predictedWinnerTeamId(Long predictedWinnerTeamId) { this.predictedWinnerTeamId = predictedWinnerTeamId; return this; }
        public Builder predictedWinnerTeamName(String predictedWinnerTeamName) { this.predictedWinnerTeamName = predictedWinnerTeamName; return this; }
        public Builder predictedTossTeamId(Long predictedTossTeamId) { this.predictedTossTeamId = predictedTossTeamId; return this; }
        public Builder predictedTossTeamName(String predictedTossTeamName) { this.predictedTossTeamName = predictedTossTeamName; return this; }
        public Builder predictedPlayerId(Long predictedPlayerId) { this.predictedPlayerId = predictedPlayerId; return this; }
        public Builder predictedPlayerName(String predictedPlayerName) { this.predictedPlayerName = predictedPlayerName; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }

        public MatchPredictionResponse build() {
            return new MatchPredictionResponse(id, matchId, userId, predictedWinnerTeamId,
                    predictedWinnerTeamName, predictedTossTeamId, predictedTossTeamName,
                    predictedPlayerId, predictedPlayerName, status, submittedAt);
        }
    }
}
