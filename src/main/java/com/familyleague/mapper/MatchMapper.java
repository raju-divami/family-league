package com.familyleague.mapper;

import com.familyleague.dto.response.MatchResponse;
import com.familyleague.dto.response.MatchResultResponse;
import com.familyleague.entity.Match;
import com.familyleague.entity.MatchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "seasonId", source = "season.id")
    @Mapping(target = "homeTeamId", source = "homeTeam.id")
    @Mapping(target = "homeTeamName", source = "homeTeam.name")
    @Mapping(target = "awayTeamId", source = "awayTeam.id")
    @Mapping(target = "awayTeamName", source = "awayTeam.name")
    MatchResponse toResponse(Match match);

    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "tossWinnerTeamId", source = "tossWinnerTeam.id")
    @Mapping(target = "tossWinnerTeamName", source = "tossWinnerTeam.name")
    @Mapping(target = "winnerTeamId", source = "winnerTeam.id")
    @Mapping(target = "winnerTeamName", source = "winnerTeam.name")
    @Mapping(target = "playerOfMatchId", source = "playerOfMatch.id")
    @Mapping(target = "playerOfMatchName", source = "playerOfMatch.fullName")
    MatchResultResponse toResultResponse(MatchResult result);
}
