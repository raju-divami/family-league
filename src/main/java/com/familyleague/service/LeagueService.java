package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.response.LeagueResponse;
import org.springframework.data.domain.Pageable;

public interface LeagueService {

    LeagueResponse createLeague(CreateLeagueRequest request);

    LeagueResponse getLeagueById(Long id);

    PagedResponse<LeagueResponse> getAllLeagues(Pageable pageable);

    void deleteLeague(Long id);
}
