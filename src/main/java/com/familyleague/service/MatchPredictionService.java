package com.familyleague.service;

import com.familyleague.dto.request.SubmitMatchPredictionRequest;
import com.familyleague.dto.response.MatchPredictionResponse;

import java.util.List;

public interface MatchPredictionService {

    MatchPredictionResponse submitPrediction(Long matchId, Long userId, SubmitMatchPredictionRequest request);

    MatchPredictionResponse updatePrediction(Long matchId, Long userId, SubmitMatchPredictionRequest request);

    MatchPredictionResponse getMyPrediction(Long matchId, Long userId);

    List<MatchPredictionResponse> getPredictionsForMatch(Long matchId, Long requestingUserId);
}
