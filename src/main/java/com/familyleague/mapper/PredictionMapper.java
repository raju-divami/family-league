package com.familyleague.mapper;

import com.familyleague.dto.response.MatchPredictionResponse;
import com.familyleague.dto.response.SeasonPredictionResponse;
import com.familyleague.entity.MatchPrediction;
import com.familyleague.entity.SeasonPrediction;
import com.familyleague.entity.SeasonPredictionPosition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PredictionMapper {

    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "predictedWinnerTeamId", source = "predictedWinnerTeam.id")
    @Mapping(target = "predictedWinnerTeamName", source = "predictedWinnerTeam.name")
    @Mapping(target = "predictedTossTeamId", source = "predictedTossTeam.id")
    @Mapping(target = "predictedTossTeamName", source = "predictedTossTeam.name")
    @Mapping(target = "predictedPlayerId", source = "predictedPlayer.id")
    @Mapping(target = "predictedPlayerName", source = "predictedPlayer.fullName")
    MatchPredictionResponse toMatchResponse(MatchPrediction prediction);

    default SeasonPredictionResponse toSeasonResponse(SeasonPrediction prediction,
                                                       List<SeasonPredictionPosition> positions) {
        List<SeasonPredictionResponse.TeamRankEntry> rankings = positions.stream()
                .map(p -> SeasonPredictionResponse.TeamRankEntry.builder()
                        .rankPosition(p.getRankPosition())
                        .teamId(p.getTeam().getId())
                        .teamName(p.getTeam().getName())
                        .build())
                .toList();
        return SeasonPredictionResponse.builder()
                .id(prediction.getId())
                .seasonId(prediction.getSeason().getId())
                .userId(prediction.getUser().getId())
                .status(prediction.getStatus())
                .submittedAt(prediction.getSubmittedAt())
                .rankings(rankings)
                .build();
    }
}
