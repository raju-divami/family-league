package com.familyleague.mapper;

import com.familyleague.dto.response.MatchResultResponse;
import com.familyleague.entity.MatchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchResultMapper {

    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "tossWinnerTeamId", source = "tossWinnerTeam.id")
    @Mapping(target = "tossWinnerTeamName", source = "tossWinnerTeam.name")
    @Mapping(target = "winnerTeamId", source = "winnerTeam.id")
    @Mapping(target = "winnerTeamName", source = "winnerTeam.name")
    @Mapping(target = "playerOfMatchId", source = "playerOfMatch.id")
    @Mapping(target = "playerOfMatchName", source = "playerOfMatch.fullName")
    MatchResultResponse toResponse(MatchResult result);
}
