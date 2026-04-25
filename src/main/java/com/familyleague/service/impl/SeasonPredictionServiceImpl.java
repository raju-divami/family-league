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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeasonPredictionServiceImpl implements SeasonPredictionService {
    
    private final SeasonPredictionRepository predictionRepository;
    private final SeasonPredictionPositionRepository positionRepository;
    private final LeagueSeasonRepository seasonRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PredictionMapper predictionMapper;

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
        
        // Predictions visible only after lock time (4 hours before first match)
        if (season.getFirstMatchStartTime() == null) {
            throw new BusinessException("Season predictions not visible until lock time is set");
        }
        
        int lockHours = season.getPredictionLockHours() != null ? season.getPredictionLockHours() : 4;
        LocalDateTime lockTime = season.getFirstMatchStartTime().minusHours(lockHours);
        
        if (LocalDateTime.now().isBefore(lockTime)) {
            throw new BusinessException("Season predictions not visible until lock time: " + lockTime);
        }
        
        List<SeasonPrediction> predictions = predictionRepository.findBySeasonId(seasonId);
        return predictions.stream()
                .map(pred -> {
                    List<SeasonPredictionPosition> positions = positionRepository.findByPredictionIdOrderByRankPosition(pred.getId());
                    return predictionMapper.toSeasonResponse(pred, positions);
                })
                .toList();
    }
}
