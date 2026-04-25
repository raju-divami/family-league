package com.familyleague.service.impl;

import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.request.UpdateLeagueRequest;
import com.familyleague.dto.response.LeagueResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.entity.League;
import com.familyleague.exception.ConflictException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.LeagueMapper;
import com.familyleague.repository.LeagueRepository;
import com.familyleague.service.LeagueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {
    
    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    @Override
    @Transactional
    public LeagueResponse createLeague(CreateLeagueRequest request) {
        log.debug("Creating league with code: {}", request.getCode());
        
        if (leagueRepository.existsByCode(request.getCode())) {
            throw new ConflictException("League with code already exists: " + request.getCode());
        }
        
        League league = League.builder()
                .code(request.getCode())
                .name(request.getName())
                .sportType(request.getSportType() != null ? request.getSportType() : "CRICKET")
                .description(request.getDescription())
                .build();
        
        league = leagueRepository.save(league);
        log.info("League created with ID: {}", league.getId());
        return leagueMapper.toResponse(league);
    }

    @Override
    @Transactional(readOnly = true)
    public LeagueResponse getLeagueById(Long id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League not found: " + id));
        return leagueMapper.toResponse(league);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<LeagueResponse> getAllLeagues(Pageable pageable) {
        Page<League> leaguePage = leagueRepository.findAll(pageable);
        return PagedResponse.<LeagueResponse>builder()
                .content(leaguePage.getContent().stream().map(leagueMapper::toResponse).toList())
                .page(leaguePage.getNumber())
                .size(leaguePage.getSize())
                .totalElements(leaguePage.getTotalElements())
                .totalPages(leaguePage.getTotalPages())
                .last(leaguePage.isLast())
                .build();
    }

    @Override
    @Transactional
    public LeagueResponse updateLeague(Long id, UpdateLeagueRequest request) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League not found: " + id));

        if (request.getName() != null) league.setName(request.getName());
        if (request.getSportType() != null) league.setSportType(request.getSportType());
        if (request.getDescription() != null) league.setDescription(request.getDescription());

        league = leagueRepository.save(league);
        log.info("League updated: {}", id);
        return leagueMapper.toResponse(league);
    }

    @Override
    @Transactional
    public void deleteLeague(Long id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League not found: " + id));
        league.setDeleted(true);
        leagueRepository.save(league);
        log.info("League soft deleted: {}", id);
    }
}
