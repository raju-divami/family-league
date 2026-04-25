package com.familyleague.mapper;

import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.entity.SeasonTeam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeasonTeamMapper {

    @Mapping(target = "seasonId", source = "season.id")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "teamCode", source = "team.code")
    SeasonTeamResponse toResponse(SeasonTeam seasonTeam);
}
