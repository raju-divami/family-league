package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for publishing the official match result")
public class PublishResultRequest {

    @Schema(description = "ID of the winning team (null if tie)", example = "1")
    private Long winnerTeamId;

    @Schema(description = "ID of the toss-winning team", example = "2")
    private Long tossWinnerTeamId;

    @Schema(description = "ID of the player of the match", example = "5")
    private Long playerOfMatchId;

    @Schema(description = "Set to true when the match ends in a tie", example = "false", defaultValue = "false")
    private Boolean isTie;

    @Schema(description = "Optional remarks about the result", example = "MI won by 6 wickets")
    private String remarks;

    public Long getWinnerTeamId() { return winnerTeamId; }
    public void setWinnerTeamId(Long winnerTeamId) { this.winnerTeamId = winnerTeamId; }
    public Long getTossWinnerTeamId() { return tossWinnerTeamId; }
    public void setTossWinnerTeamId(Long tossWinnerTeamId) { this.tossWinnerTeamId = tossWinnerTeamId; }
    public Long getPlayerOfMatchId() { return playerOfMatchId; }
    public void setPlayerOfMatchId(Long playerOfMatchId) { this.playerOfMatchId = playerOfMatchId; }
    public Boolean getIsTie() { return isTie; }
    public void setIsTie(Boolean isTie) { this.isTie = isTie; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
