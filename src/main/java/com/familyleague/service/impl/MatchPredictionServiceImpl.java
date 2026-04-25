package com.familyleague.service.impl;

import com.familyleague.dto.request.SubmitMatchPredictionRequest;
import com.familyleague.dto.response.MatchPredictionResponse;
import com.familyleague.entity.Match;
import com.familyleague.entity.MatchPrediction;
import com.familyleague.entity.Player;
import com.familyleague.entity.Team;
import com.familyleague.entity.User;
import com.familyleague.exception.BusinessException;
import com.familyleague.exception.PredictionLockedException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.PredictionMapper;
import com.familyleague.repository.MatchPredictionRepository;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.PlayerRepository;
import com.familyleague.repository.TeamRepository;
import com.familyleague.repository.UserRepository;
import com.familyleague.service.MatchPredictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchPredictionServiceImpl implements MatchPredictionService {
    
    private final MatchPredictionRepository predictionRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PredictionMapper predictionMapper;

    @Override
    @Transactional
    public MatchPredictionResponse submitPrediction(Long matchId, Long userId, SubmitMatchPredictionRequest request) {
        log.debug("Submitting prediction for match: {} by user: {}", matchId, userId);
        
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        
        if (LocalDateTime.now().isAfter(match.getPredictionLockTime())) {
            throw new PredictionLockedException("Predictions are locked for this match");
        }
        
        if (predictionRepository.existsByMatchIdAndUserId(matchId, userId)) {
            throw new BusinessException("Prediction already submitted. Use update instead");
        }
        
        MatchPrediction.MatchPredictionBuilder builder = MatchPrediction.builder()
                .match(match)
                .season(match.getSeason())
                .user(user)
                .status("SUBMITTED")
                .submittedAt(LocalDateTime.now());
        
        if (request.getPredictedWinnerTeamId() != null) {
            Team winnerTeam = teamRepository.findById(request.getPredictedWinnerTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            builder.predictedWinnerTeam(winnerTeam);
        }
        
        if (request.getPredictedTossTeamId() != null) {
            Team tossTeam = teamRepository.findById(request.getPredictedTossTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            builder.predictedTossTeam(tossTeam);
        }
        
        if (request.getPredictedPlayerId() != null) {
            Player player = playerRepository.findById(request.getPredictedPlayerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
            builder.predictedPlayer(player);
        }
        
        MatchPrediction prediction = predictionRepository.save(builder.build());
        log.info("Prediction submitted for match: {} by user: {}", matchId, userId);
        return predictionMapper.toMatchResponse(prediction);
    }

    @Override
    @Transactional
    public MatchPredictionResponse updatePrediction(Long matchId, Long userId, SubmitMatchPredictionRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        
        if (LocalDateTime.now().isAfter(match.getPredictionLockTime())) {
            throw new PredictionLockedException("Predictions are locked for this match");
        }
        
        MatchPrediction prediction = predictionRepository.findByMatchIdAndUserId(matchId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Prediction not found"));
        
        if (request.getPredictedWinnerTeamId() != null) {
            Team winnerTeam = teamRepository.findById(request.getPredictedWinnerTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            prediction.setPredictedWinnerTeam(winnerTeam);
        }
        
        if (request.getPredictedTossTeamId() != null) {
            Team tossTeam = teamRepository.findById(request.getPredictedTossTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            prediction.setPredictedTossTeam(tossTeam);
        }
        
        if (request.getPredictedPlayerId() != null) {
            Player player = playerRepository.findById(request.getPredictedPlayerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
            prediction.setPredictedPlayer(player);
        }
        
        prediction = predictionRepository.save(prediction);
        log.info("Prediction updated for match: {} by user: {}", matchId, userId);
        return predictionMapper.toMatchResponse(prediction);
    }

    @Override
    @Transactional(readOnly = true)
    public MatchPredictionResponse getMyPrediction(Long matchId, Long userId) {
        MatchPrediction prediction = predictionRepository.findByMatchIdAndUserId(matchId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Prediction not found"));
        return predictionMapper.toMatchResponse(prediction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchPredictionResponse> getPredictionsForMatch(Long matchId, Long requestingUserId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        
        // Predictions visible only after lock time per requirements
        if (LocalDateTime.now().isBefore(match.getPredictionLockTime())) {
            throw new BusinessException("Predictions not visible until lock time");
        }
        
        List<MatchPrediction> predictions = predictionRepository.findByMatchId(matchId);
        return predictions.stream().map(predictionMapper::toMatchResponse).toList();
    }
}
