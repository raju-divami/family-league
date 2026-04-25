package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreatePlayerRequest;
import com.familyleague.dto.request.UpdatePlayerRequest;
import com.familyleague.dto.response.PlayerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {

    PlayerResponse createPlayer(CreatePlayerRequest request);

    PlayerResponse getPlayerById(Long id);

    PagedResponse<PlayerResponse> getAllPlayers(Pageable pageable);

    PlayerResponse updatePlayer(Long id, UpdatePlayerRequest request);

    void addPlayerToTeam(Long seasonTeamId, Long playerId);

    List<PlayerResponse> getPlayersBySeasonTeam(Long seasonTeamId);
}
