package com.familyleague.service.impl;

import com.familyleague.dto.request.SubmitSeasonPredictionRequest;
import com.familyleague.dto.response.SeasonPredictionResponse;
import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.SeasonPrediction;
import com.familyleague.entity.SeasonPredictionPosition;
import com.familyleague.entity.Team;
import com.familyleague.entity.User;
import com.familyleague.exception.BusinessException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.PredictionMapper;
import com.familyleague.repository.LeagueSeasonRepository;
import com.familyleague.repository.SeasonPredictionPositionRepository;
import com.familyleague.repository.SeasonPredictionRepository;
import com.familyleague.repository.TeamRepository;
import com.familyleague.repository.UserRepository;
import com.familyleague.service.SeasonPredictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeasonPredictionServiceImpl implements SeasonPredictionService {

    private static final Logger log = LoggerFactory.getLogger(SeasonPredictionServiceImpl.class);

    private final SeasonPredictionRepository predictionRepository;
    private final SeasonPredictionPositionRepository positionRepository;
    private final LeagueSeasonRepository seasonRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PredictionMapper predictionMapper;

    public SeasonPredictionServiceImpl(SeasonPredictionRepository predictionRepository,
                                       SeasonPredictionPositionRepository positionRepository,
                                       LeagueSeasonRepository seasonRepository,
                                       UserRepository userRepository,
                                       TeamRepository teamRepository,
                                       PredictionMapper predictionMapper) {
        this.predictionRepository = predictionRepository;
        this.positionRepository = positionRepository;
        this.seasonRepository = seasonRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.predictionMapper = predictionMapper;
    }

    @Override
    @Transactional
    public SeasonPredictionResponse submitPrediction(Long seasonId, Long userId, SubmitSeasonPredictionRequest request) {
        log.debug("Submitting season prediction for season: {} by user: {}", seasonId, userId);

        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // Check if predictions are locked (4 hours before first match or when first match starts)
        if (season.getFirstMatchStartTime() != null) {
            int lockHours = season.getPredictionLockHours() != null ? season.getPredictionLockHours() : 4;
            LocalDateTime lockTime = season.getFirstMatchStartTime().minusHours(lockHours);
            if (LocalDateTime.now().isAfter(lockTime)) {
                throw new BusinessException("Season predictions are locked. Lock time was: " + lockTime);
            }
        }

        // Check if prediction already exists (one per user per season)
        SeasonPrediction existingPrediction = predictionRepository.findBySeasonIdAndUserId(seasonId, userId)
                .orElse(null);

        if (existingPrediction != null && "LOCKED".equals(existingPrediction.getStatus())) {
            throw new BusinessException("Cannot modify locked prediction");
        }

        // Create or update prediction
        SeasonPrediction prediction = existingPrediction != null ? existingPrediction :
                SeasonPrediction.builder()
                        .season(season)
                        .user(user)
                        .status("SUBMITTED")
                        .submittedAt(LocalDateTime.now())
                        .build();

        prediction = predictionRepository.save(prediction);

        // Delete existing positions and create new ones
        positionRepository.deleteByPredictionId(prediction.getId());

        for (SubmitSeasonPredictionRequest.TeamRankEntry entry : request.getRankings()) {
            Team team = teamRepository.findById(entry.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + entry.getTeamId()));

            SeasonPredictionPosition position = SeasonPredictionPosition.builder()
                    .prediction(prediction)
                    .team(team)
                    .rankPosition(entry.getRankPosition())
                    .build();

            positionRepository.save(position);
        }

        // Fetch positions for response
        List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(prediction.getId());

        log.info("Season prediction submitted for season: {} by user: {}", seasonId, userId);
        return predictionMapper.toSeasonResponse(prediction, positions);
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonPredictionResponse getMyPrediction(Long seasonId, Long userId) {
        SeasonPrediction prediction = predictionRepository.findBySeasonIdAndUserId(seasonId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Season prediction not found"));
        List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(prediction.getId());
        return predictionMapper.toSeasonResponse(prediction, positions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeasonPredictionResponse> getPredictionsForSeason(Long seasonId, Long requestingUserId) {
        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));

        // Calculate lock time
        if (season.getFirstMatchStartTime() == null) {
            log.debug("No first match time set - returning only requesting user's prediction");
            // If no first match time, return only requesting user's prediction
            return predictionRepository.findBySeasonIdAndUserId(seasonId, requestingUserId)
                    .map(pred -> {
                        List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(pred.getId());
                        return predictionMapper.toSeasonResponse(pred, positions);
                    })
                    .map(List::of)
                    .orElse(List.of());
        }

        int lockHours = season.getPredictionLockHours() != null ? season.getPredictionLockHours() : 4;
        LocalDateTime lockTime = season.getFirstMatchStartTime().minusHours(lockHours);

        // Before lock: show only requesting user's prediction
        // After lock: show all predictions
        if (LocalDateTime.now().isBefore(lockTime)) {
            log.debug("Before lock time - returning only requesting user's prediction for season: {}", seasonId);
            // Return only the requesting user's prediction if it exists
            return predictionRepository.findBySeasonIdAndUserId(seasonId, requestingUserId)
                    .map(pred -> {
                        List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(pred.getId());
                        return predictionMapper.toSeasonResponse(pred, positions);
                    })
                    .map(List::of)
                    .orElse(List.of()); // Return empty list if user hasn't predicted yet
        }

        log.debug("After lock time - returning all predictions for season: {}", seasonId);
        List<SeasonPrediction> predictions = predictionRepository.findBySeasonId(seasonId);
        return predictions.stream()
                .map(pred -> {
                    List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(pred.getId());
                    return predictionMapper.toSeasonResponse(pred, positions);
                })
                .toList();
    }
}
