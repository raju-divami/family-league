package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LeaderboardResponse {

    private Long id;
    private Long seasonId;
    private Long userId;
    private String userDisplayName;
    private Integer totalPoints;
    private Integer rankNo;
    private LocalDateTime recalculatedAt;
}
