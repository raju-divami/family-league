package com.familyleague.service;

import com.familyleague.dto.request.PublishResultRequest;
import com.familyleague.dto.response.MatchResultResponse;

public interface MatchResultService {

    MatchResultResponse publishResult(Long matchId, PublishResultRequest request, Long adminId);

    MatchResultResponse getResultByMatchId(Long matchId);
}
