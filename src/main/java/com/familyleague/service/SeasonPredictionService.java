package com.familyleague.service;

import com.familyleague.dto.request.SubmitSeasonPredictionRequest;
import com.familyleague.dto.response.SeasonPredictionResponse;

import java.util.List;

public interface SeasonPredictionService {

    SeasonPredictionResponse submitPrediction(Long seasonId, Long userId, SubmitSeasonPredictionRequest request);

    SeasonPredictionResponse getMyPrediction(Long seasonId, Long userId);

    List<SeasonPredictionResponse> getPredictionsForSeason(Long seasonId, Long requestingUserId);
}
