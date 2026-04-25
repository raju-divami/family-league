package com.familyleague.mapper;

import com.familyleague.dto.request.CreateTeamRequest;
import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.dto.response.TeamResponse;
import com.familyleague.entity.SeasonTeam;
import com.familyleague.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toEntity(CreateTeamRequest request);

    TeamResponse toResponse(Team team);

    @Mapping(target = "seasonId", source = "season.id")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "teamCode", source = "team.code")
    SeasonTeamResponse toSeasonTeamResponse(SeasonTeam seasonTeam);
}
