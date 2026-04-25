package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateSeasonRequest;
import com.familyleague.dto.request.UpdateSeasonStatusRequest;
import com.familyleague.dto.response.LeagueSeasonResponse;
import org.springframework.data.domain.Pageable;

public interface LeagueSeasonService {

    LeagueSeasonResponse createSeason(Long leagueId, CreateSeasonRequest request);

    LeagueSeasonResponse getSeasonById(Long seasonId);

    PagedResponse<LeagueSeasonResponse> getSeasonsByLeague(Long leagueId, Pageable pageable);

    LeagueSeasonResponse updateStatus(Long seasonId, UpdateSeasonStatusRequest request);

    void joinSeason(Long seasonId, Long userId);

    LeagueSeasonResponse openSeason(Long seasonId);

    void closeSeason(Long seasonId);

    PagedResponse<LeagueSeasonResponse> getAllSeasons(Pageable pageable);
}
