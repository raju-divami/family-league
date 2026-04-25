package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateMatchRequest;
import com.familyleague.dto.request.UpdateMatchRequest;
import com.familyleague.dto.response.MatchResponse;
import org.springframework.data.domain.Pageable;

public interface MatchService {

    MatchResponse createMatch(Long seasonId, CreateMatchRequest request);

    MatchResponse getMatchById(Long id);

    PagedResponse<MatchResponse> getMatchesBySeason(Long seasonId, Pageable pageable);

    PagedResponse<MatchResponse> getAllMatches(Long seasonId, Pageable pageable);

    MatchResponse updateMatch(Long id, UpdateMatchRequest request);

    void lockMatch(Long matchId);
}
