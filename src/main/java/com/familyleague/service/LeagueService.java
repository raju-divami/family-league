package com.familyleague.service;

import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.request.UpdateLeagueRequest;
import com.familyleague.dto.response.LeagueResponse;
import com.familyleague.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface LeagueService {

    LeagueResponse createLeague(CreateLeagueRequest request);

    LeagueResponse getLeagueById(Long id);

    PagedResponse<LeagueResponse> getAllLeagues(Pageable pageable);

    LeagueResponse updateLeague(Long id, UpdateLeagueRequest request);

    void deleteLeague(Long id);
}
