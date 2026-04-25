package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreatePlayerRequest;
import com.familyleague.dto.request.UpdatePlayerRequest;
import com.familyleague.dto.response.PlayerResponse;
import com.familyleague.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Players")
@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/admin/players")
    @Operation(summary = "Create a player")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<PlayerResponse>> createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Player created", playerService.createPlayer(request)));
    }

    @PutMapping("/admin/players/{id}")
    @Operation(summary = "Update a player")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<PlayerResponse>> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlayerRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Player updated", playerService.updatePlayer(id, request)));
    }

    @GetMapping("/api/players/{id}")
    @Operation(summary = "Get player by ID")
    public ResponseEntity<ApiResponse<PlayerResponse>> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(playerService.getPlayerById(id)));
    }

    @GetMapping("/api/players")
    @Operation(summary = "List all players")
    public ResponseEntity<ApiResponse<PagedResponse<PlayerResponse>>> getAllPlayers(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(playerService.getAllPlayers(pageable)));
    }

    @PostMapping("/admin/season-teams/{seasonTeamId}/players/{playerId}")
    @Operation(summary = "Add player to a season team")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> addPlayerToTeam(
            @PathVariable Long seasonTeamId, @PathVariable Long playerId) {
        playerService.addPlayerToTeam(seasonTeamId, playerId);
        return ResponseEntity.ok(ApiResponse.ok("Player added to team"));
    }

    @GetMapping("/api/season-teams/{seasonTeamId}/players")
    @Operation(summary = "Get players in a season team")
    public ResponseEntity<ApiResponse<List<PlayerResponse>>> getPlayersBySeasonTeam(
            @PathVariable Long seasonTeamId) {
        return ResponseEntity.ok(ApiResponse.ok(playerService.getPlayersBySeasonTeam(seasonTeamId)));
    }
}
