package com.familyleague.service.impl;

import com.familyleague.common.enums.SeasonStatus;
import com.familyleague.dto.request.CreateSeasonRequest;
import com.familyleague.dto.request.UpdateSeasonStatusRequest;
import com.familyleague.dto.response.LeagueSeasonResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.entity.League;
import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.SeasonMember;
import com.familyleague.entity.User;
import com.familyleague.exception.BusinessException;
import com.familyleague.exception.ConflictException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.LeagueSeasonMapper;
import com.familyleague.repository.LeagueRepository;
import com.familyleague.repository.LeagueSeasonRepository;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.SeasonMemberRepository;
import com.familyleague.repository.UserRepository;
import com.familyleague.service.LeagueSeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LeagueSeasonServiceImpl implements LeagueSeasonService {

    private static final Logger log = LoggerFactory.getLogger(LeagueSeasonServiceImpl.class);

    private final LeagueSeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final SeasonMemberRepository seasonMemberRepository;
    private final MatchRepository matchRepository;
    private final LeagueSeasonMapper seasonMapper;

    public LeagueSeasonServiceImpl(LeagueSeasonRepository seasonRepository,
                                   LeagueRepository leagueRepository,
                                   UserRepository userRepository,
                                   SeasonMemberRepository seasonMemberRepository,
                                   MatchRepository matchRepository,
                                   LeagueSeasonMapper seasonMapper) {
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
        this.seasonMemberRepository = seasonMemberRepository;
        this.matchRepository = matchRepository;
        this.seasonMapper = seasonMapper;
    }

    @Override
    @Transactional
    public LeagueSeasonResponse createSeason(Long leagueId, CreateSeasonRequest request) {
        log.debug("Creating season for league: {}", leagueId);

        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League not found: " + leagueId));

        LeagueSeason season = LeagueSeason.builder()
                .league(league)
                .seasonCode(request.getSeasonCode())
                .seasonName(request.getSeasonName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(SeasonStatus.DRAFT.name())
                .matchPredictionLockHours(request.getMatchPredictionLockHours() != null ?
                        request.getMatchPredictionLockHours() : 1)
                .build();

        season = seasonRepository.save(season);
        log.info("Season created with ID: {}", season.getId());
        return seasonMapper.toResponse(season);
    }

    @Override
    @Transactional(readOnly = true)
    public LeagueSeasonResponse getSeasonById(Long seasonId) {
        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));
        return seasonMapper.toResponse(season);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<LeagueSeasonResponse> getSeasonsByLeague(Long leagueId, Pageable pageable) {
        Page<LeagueSeason> seasonPage = seasonRepository.findByLeagueIdAndDeletedFalse(leagueId, pageable);
        return PagedResponse.<LeagueSeasonResponse>builder()
                .content(seasonPage.getContent().stream().map(seasonMapper::toResponse).toList())
                .page(seasonPage.getNumber())
                .size(seasonPage.getSize())
                .totalElements(seasonPage.getTotalElements())
                .totalPages(seasonPage.getTotalPages())
                .last(seasonPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public LeagueSeasonResponse updateStatus(Long seasonId, UpdateSeasonStatusRequest request) {
        log.debug("Updating season {} status to {}", seasonId, request.getStatus());

        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));

        // Validate status transition
        SeasonStatus currentStatus;
        SeasonStatus targetStatus;

        try {
            currentStatus = SeasonStatus.valueOf(season.getStatus());
            targetStatus = SeasonStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid status value: " + request.getStatus());
        }

        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new BusinessException(String.format(
                "Invalid status transition from %s to %s. Allowed transitions: %s",
                currentStatus, targetStatus, currentStatus.getValidTransitions()));
        }

        season.setStatus(targetStatus.name());
        season = seasonRepository.save(season);
        log.info("Season status updated from {} to {}", currentStatus, targetStatus);
        return seasonMapper.toResponse(season);
    }

    @Override
    @Transactional
    public void joinSeason(Long seasonId, Long userId) {
        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (seasonMemberRepository.existsBySeasonIdAndUserId(seasonId, userId)) {
            throw new ConflictException("User already joined this season");
        }

        SeasonMember member = SeasonMember.builder()
                .season(season)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();

        seasonMemberRepository.save(member);
        log.info("User {} joined season {}", userId, seasonId);
    }

    @Override
    @Transactional
    public LeagueSeasonResponse openSeason(Long seasonId) {
        log.debug("Opening season: {}", seasonId);

        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));

        SeasonStatus currentStatus = SeasonStatus.valueOf(season.getStatus());
        SeasonStatus targetStatus = SeasonStatus.OPEN;

        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new BusinessException(String.format(
                "Cannot open season from status %s. Current status must be DRAFT", currentStatus));
        }

        season.setStatus(targetStatus.name());
        season = seasonRepository.save(season);
        log.info("Season {} opened successfully", seasonId);
        return seasonMapper.toResponse(season);
    }

    @Override
    @Transactional
    public void closeSeason(Long seasonId) {
        log.debug("Closing season: {}", seasonId);

        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));

        SeasonStatus currentStatus = SeasonStatus.valueOf(season.getStatus());
        SeasonStatus targetStatus = SeasonStatus.CLOSED;

        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new BusinessException(String.format(
                "Cannot close season from status %s. Current status must be COMPLETED", currentStatus));
        }

        season.setStatus(targetStatus.name());
        season.setClosedAt(LocalDateTime.now());
        seasonRepository.save(season);
        log.info("Season {} closed successfully", seasonId);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<LeagueSeasonResponse> getAllSeasons(Pageable pageable) {
        log.debug("Fetching all seasons, page: {}", pageable.getPageNumber());

        Page<LeagueSeason> seasonPage = seasonRepository.findAllByDeletedFalse(pageable);
        return PagedResponse.<LeagueSeasonResponse>builder()
                .content(seasonPage.getContent().stream().map(seasonMapper::toResponse).toList())
                .page(seasonPage.getNumber())
                .size(seasonPage.getSize())
                .totalElements(seasonPage.getTotalElements())
                .totalPages(seasonPage.getTotalPages())
                .last(seasonPage.isLast())
                .build();
    }
}
