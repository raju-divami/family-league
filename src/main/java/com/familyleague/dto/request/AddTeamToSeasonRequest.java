package com.familyleague.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTeamToSeasonRequest {

    @NotNull
    private Long teamId;

    private Integer seedRank;
}
