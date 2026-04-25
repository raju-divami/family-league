package com.familyleague.mapper;

import com.familyleague.dto.response.LeagueSeasonResponse;
import com.familyleague.entity.LeagueSeason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeagueSeasonMapper {

    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "leagueName", source = "league.name")
    LeagueSeasonResponse toResponse(LeagueSeason season);
}
