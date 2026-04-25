package com.familyleague.service.impl;
import com.familyleague.dto.request.CreateMatchRequest;
import com.familyleague.dto.request.UpdateMatchRequest;
import com.familyleague.dto.response.MatchResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.Match;
import com.familyleague.entity.Team;
import com.familyleague.exception.BusinessException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.MatchMapper;
import com.familyleague.repository.LeagueSeasonRepository;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.TeamRepository;
import com.familyleague.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final LeagueSeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final MatchMapper matchMapper;
    @Override
    @Transactional
    public MatchResponse createMatch(Long seasonId, CreateMatchRequest request) {
        log.debug("Creating match for season: {}", seasonId);
        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));
        Team homeTeam = teamRepository.findById(request.getHomeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Home team not found"));
        Team awayTeam = teamRepository.findById(request.getAwayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Away team not found"));
        LocalDateTime lockTime = request.getStartTime()
                .minusHours(season.getMatchPredictionLockHours() != null ? 
                        season.getMatchPredictionLockHours() : 1);
        Match match = Match.builder()
                .season(season)
                .matchNo(request.getMatchNo())
                .stage(request.getStage())
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .venue(request.getVenue())
                .startTime(request.getStartTime())
                .predictionLockTime(lockTime)
                .status("SCHEDULED")
                .build();
        match = matchRepository.save(match);
        log.info("Match created with ID: {}", match.getId());
        return matchMapper.toResponse(match);
    }
    @Override
    @Transactional(readOnly = true)
    public MatchResponse getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        return matchMapper.toResponse(match);
    }
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MatchResponse> getMatchesBySeason(Long seasonId, Pageable pageable) {
        Page<Match> matchPage = matchRepository.findBySeasonIdOrderByStartTimeAsc(seasonId, pageable);
        return PagedResponse.<MatchResponse>builder()
                .content(matchPage.getContent().stream().map(matchMapper::toResponse).toList())
                .page(matchPage.getNumber())
                .size(matchPage.getSize())
                .totalElements(matchPage.getTotalElements())
                .totalPages(matchPage.getTotalPages())
                .last(matchPage.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MatchResponse> getAllMatches(Long seasonId, Pageable pageable) {
        if (seasonId != null) {
            return getMatchesBySeason(seasonId, pageable);
        }
        Page<Match> matchPage = matchRepository.findAll(pageable);
        return PagedResponse.<MatchResponse>builder()
                .content(matchPage.getContent().stream().map(matchMapper::toResponse).toList())
                .page(matchPage.getNumber())
                .size(matchPage.getSize())
                .totalElements(matchPage.getTotalElements())
                .totalPages(matchPage.getTotalPages())
                .last(matchPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public MatchResponse updateMatch(Long id, UpdateMatchRequest request) {
        log.debug("Updating match: {}", id);
        
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        
        boolean startTimeChanged = false;
        
        if (request.getMatchNo() != null) {
            match.setMatchNo(request.getMatchNo());
        }
        if (request.getStage() != null) {
            match.setStage(request.getStage());
        }
        if (request.getHomeTeamId() != null) {
            Team homeTeam = teamRepository.findById(request.getHomeTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Home team not found"));
            match.setHomeTeam(homeTeam);
        }
        if (request.getAwayTeamId() != null) {
            Team awayTeam = teamRepository.findById(request.getAwayTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Away team not found"));
            match.setAwayTeam(awayTeam);
        }
        if (request.getVenue() != null) {
            match.setVenue(request.getVenue());
        }
        if (request.getStartTime() != null) {
            match.setStartTime(request.getStartTime());
            startTimeChanged = true;
        }
        
        // Recalculate predictionLockTime if startTime changed
        if (startTimeChanged) {
            LeagueSeason season = match.getSeason();
            LocalDateTime lockTime = match.getStartTime()
                    .minusHours(season.getMatchPredictionLockHours() != null ? 
                            season.getMatchPredictionLockHours() : 1);
            match.setPredictionLockTime(lockTime);
        }
        
        match = matchRepository.save(match);
        log.info("Match updated: {}", match.getId());
        return matchMapper.toResponse(match);
    }
    
    @Override
    @Transactional
    public void lockMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        if (LocalDateTime.now().isBefore(match.getPredictionLockTime())) {
            throw new BusinessException("Cannot lock match before lock time");
        }
        match.setStatus("LOCKED");
        matchRepository.save(match);
        log.info("Match locked: {}", matchId);
    }
}
