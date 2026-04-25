package com.familyleague.controller;

import com.familyleague.dto.request.CreatePlayerRequest;
import com.familyleague.dto.request.UpdatePlayerRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.PlayerResponse;
import com.familyleague.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create a player", description = "Creates a global player record. Player code must be unique.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Player created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Player code already exists")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PlayerResponse>> createPlayer(
            @Valid @RequestBody CreatePlayerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponse.ok("Player created",
                        playerService.createPlayer(request)));
    }

    @PutMapping("/admin/players/{id}")
    @Operation(summary = "Update a player")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Player updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PlayerResponse>> updatePlayer(
            @Parameter(description = "Player ID") @PathVariable Long id,
            @Valid @RequestBody UpdatePlayerRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Player updated",
                playerService.updatePlayer(id, request)));
    }

    @GetMapping("/api/players/{id}")
    @Operation(summary = "Get player by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Player found"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PlayerResponse>> getPlayer(
            @Parameter(description = "Player ID") @PathVariable Long id) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                playerService.getPlayerById(id)));
    }

    @GetMapping("/api/players")
    @Operation(summary = "List all players", description = "Returns all non-deleted players, paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Players returned")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<PlayerResponse>>> getAllPlayers(
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                playerService.getAllPlayers(pageable)));
    }

    @PostMapping("/admin/season-teams/{seasonTeamId}/players/{playerId}")
    @Operation(summary = "Add player to a season team",
            description = "Links an existing player to a team's season roster.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Player added to team"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season team or player not found"),
            @ApiResponse(responseCode = "409", description = "Player already in this team roster")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> addPlayerToTeam(
            @Parameter(description = "Season team ID") @PathVariable Long seasonTeamId,
            @Parameter(description = "Player ID") @PathVariable Long playerId) {
        playerService.addPlayerToTeam(seasonTeamId, playerId);
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Player added to team"));
    }

    @GetMapping("/api/season-teams/{seasonTeamId}/players")
    @Operation(summary = "Get players in a season team")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Players returned"),
            @ApiResponse(responseCode = "404", description = "Season team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<List<PlayerResponse>>> getPlayersBySeasonTeam(
            @Parameter(description = "Season team ID") @PathVariable Long seasonTeamId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                playerService.getPlayersBySeasonTeam(seasonTeamId)));
    }
}
