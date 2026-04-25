package com.familyleague.mapper;

import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.request.CreateSeasonRequest;
import com.familyleague.dto.response.LeagueResponse;
import com.familyleague.dto.response.LeagueSeasonResponse;
import com.familyleague.entity.League;
import com.familyleague.entity.LeagueSeason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    League toEntity(CreateLeagueRequest request);

    LeagueResponse toResponse(League league);

    @Mapping(target = "league", ignore = true)
    LeagueSeason toSeasonEntity(CreateSeasonRequest request);

    @Mapping(target = "leagueId", source = "league.id")
    @Mapping(target = "leagueName", source = "league.name")
    LeagueSeasonResponse toSeasonResponse(LeagueSeason season);
}
