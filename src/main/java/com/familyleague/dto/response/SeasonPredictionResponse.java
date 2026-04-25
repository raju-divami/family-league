package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SeasonPredictionResponse {

    private Long id;
    private Long seasonId;
    private Long userId;
    private String status;
    private LocalDateTime submittedAt;
    private List<TeamRankEntry> rankings;

    @Getter
    @Builder
    public static class TeamRankEntry {
        private Integer rankPosition;
        private Long teamId;
        private String teamName;
    }
}
