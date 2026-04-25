package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeasonTeamResponse {

    private Long id;
    private Long seasonId;
    private Long teamId;
    private String teamName;
    private String teamCode;
    private Integer seedRank;
}
