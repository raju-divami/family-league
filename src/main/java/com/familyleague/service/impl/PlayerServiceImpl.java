package com.familyleague.service.impl;

import com.familyleague.dto.request.CreatePlayerRequest;
import com.familyleague.dto.request.UpdatePlayerRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.PlayerResponse;
import com.familyleague.entity.Player;
import com.familyleague.entity.SeasonTeam;
import com.familyleague.entity.TeamPlayer;
import com.familyleague.exception.ConflictException;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.PlayerMapper;
import com.familyleague.repository.PlayerRepository;
import com.familyleague.repository.TeamPlayerRepository;
import com.familyleague.repository.SeasonTeamRepository;
import com.familyleague.service.PlayerService;
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
public class PlayerServiceImpl implements PlayerService {
    
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final PlayerMapper playerMapper;

    @Override
    @Transactional
    public PlayerResponse createPlayer(CreatePlayerRequest request) {
        log.debug("Creating player: {}", request.getFullName());
        
        Player player = Player.builder()
                .code(request.getCode())
                .fullName(request.getFullName())
                .shortName(request.getShortName())
                .country(request.getCountry())
                .build();
        
        player = playerRepository.save(player);
        log.info("Player created with ID: {}", player.getId());
        return playerMapper.toResponse(player);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerResponse getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + id));
        return playerMapper.toResponse(player);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PlayerResponse> getAllPlayers(Pageable pageable) {
        Page<Player> playerPage = playerRepository.findByDeletedFalse(pageable);
        return PagedResponse.<PlayerResponse>builder()
                .content(playerPage.getContent().stream().map(playerMapper::toResponse).toList())
                .page(playerPage.getNumber())
                .size(playerPage.getSize())
                .totalElements(playerPage.getTotalElements())
                .totalPages(playerPage.getTotalPages())
                .last(playerPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public PlayerResponse updatePlayer(Long id, UpdatePlayerRequest request) {
        log.debug("Updating player: {}", id);
        
        Player player = playerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + id));
        
        if (request.getFullName() != null) {
            player.setFullName(request.getFullName());
        }
        if (request.getShortName() != null) {
            player.setShortName(request.getShortName());
        }
        if (request.getCountry() != null) {
            player.setCountry(request.getCountry());
        }
        
        player = playerRepository.save(player);
        log.info("Player updated: {}", player.getId());
        return playerMapper.toResponse(player);
    }

    @Override
    @Transactional
    public void addPlayerToTeam(Long seasonTeamId, Long playerId) {
        SeasonTeam seasonTeam = seasonTeamRepository.findById(seasonTeamId)
                .orElseThrow(() -> new ResourceNotFoundException("Season team not found: " + seasonTeamId));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + playerId));
        
        if (teamPlayerRepository.existsBySeasonTeamIdAndPlayerId(seasonTeamId, playerId)) {
            throw new ConflictException("Player already added to this season team");
        }
        
        TeamPlayer tp = TeamPlayer.builder()
                .seasonTeam(seasonTeam)
                .player(player)
                .build();
        
        teamPlayerRepository.save(tp);
        log.info("Player {} added to season team {}", playerId, seasonTeamId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponse> getPlayersBySeasonTeam(Long seasonTeamId) {
        List<TeamPlayer> tpList = teamPlayerRepository.findBySeasonTeamId(seasonTeamId);
        return tpList.stream()
                .map(tp -> playerMapper.toResponse(tp.getPlayer()))
                .toList();
    }
}
