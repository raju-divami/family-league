package com.familyleague.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitSeasonPredictionRequest {

    @NotEmpty
    private List<TeamRankEntry> rankings;

    @Getter
    @Setter
    public static class TeamRankEntry {

        @NotNull
        private Long teamId;

        @NotNull
        private Integer rankPosition;
    }
}
