package com.familyleague.service.impl;

import com.familyleague.dto.request.AddTeamToSeasonRequest;
import com.familyleague.dto.request.CreateTeamRequest;
import com.familyleague.dto.request.UpdateTeamRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.dto.response.TeamResponse;
import com.familyleague.entity.LeagueSeason;
import com.familyleague.entity.SeasonTeam;
import com.familyleague.entity.Team;
import com.familyleague.exception.ConflictException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.SeasonTeamMapper;
import com.familyleague.mapper.TeamMapper;
import com.familyleague.repository.LeagueSeasonRepository;
import com.familyleague.repository.SeasonTeamRepository;
import com.familyleague.repository.TeamRepository;
import com.familyleague.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    
    private final TeamRepository teamRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final LeagueSeasonRepository seasonRepository;
    private final TeamMapper teamMapper;
    private final SeasonTeamMapper seasonTeamMapper;

    @Override
    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request) {
        log.debug("Creating team: {}", request.getName());
        
        if (teamRepository.existsByCode(request.getCode())) {
            throw new ConflictException("Team with code already exists: " + request.getCode());
        }
        
        Team team = Team.builder()
                .code(request.getCode())
                .name(request.getName())
                .shortName(request.getShortName())
                .logoUrl(request.getLogoUrl())
                .build();
        
        team = teamRepository.save(team);
        log.info("Team created with ID: {}", team.getId());
        return teamMapper.toResponse(team);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
        return teamMapper.toResponse(team);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TeamResponse> getAllTeams(Pageable pageable) {
        Page<Team> teamPage = teamRepository.findByDeletedFalse(pageable);
        return PagedResponse.<TeamResponse>builder()
                .content(teamPage.getContent().stream().map(teamMapper::toResponse).toList())
                .page(teamPage.getNumber())
                .size(teamPage.getSize())
                .totalElements(teamPage.getTotalElements())
                .totalPages(teamPage.getTotalPages())
                .last(teamPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public TeamResponse updateTeam(Long id, UpdateTeamRequest request) {
        log.debug("Updating team: {}", id);
        
        Team team = teamRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
        
        if (request.getName() != null) {
            team.setName(request.getName());
        }
        if (request.getShortName() != null) {
            team.setShortName(request.getShortName());
        }
        if (request.getLogoUrl() != null) {
            team.setLogoUrl(request.getLogoUrl());
        }
        
        team = teamRepository.save(team);
        log.info("Team updated: {}", team.getId());
        return teamMapper.toResponse(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        log.debug("Soft deleting team: {}", id);
        
        Team team = teamRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
        
        team.setDeleted(true);
        teamRepository.save(team);
        log.info("Team soft deleted: {}", id);
    }

    @Override
    @Transactional
    public SeasonTeamResponse addTeamToSeason(Long seasonId, AddTeamToSeasonRequest request) {
        LeagueSeason season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found: " + seasonId));
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + request.getTeamId()));
        
        if (seasonTeamRepository.existsBySeasonIdAndTeamId(seasonId, request.getTeamId())) {
            throw new ConflictException("Team already added to this season");
        }
        
        SeasonTeam seasonTeam = SeasonTeam.builder()
                .season(season)
                .team(team)
                .build();
        
        seasonTeam = seasonTeamRepository.save(seasonTeam);
        log.info("Team {} added to season {}", request.getTeamId(), seasonId);
        return seasonTeamMapper.toResponse(seasonTeam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeasonTeamResponse> getTeamsBySeason(Long seasonId) {
        List<SeasonTeam> seasonTeams = seasonTeamRepository.findBySeasonId(seasonId);
        return seasonTeams.stream().map(seasonTeamMapper::toResponse).toList();
    }
}
