package com.familyleague.controller;

import com.familyleague.dto.request.CreateSeasonRequest;
import com.familyleague.dto.request.UpdateSeasonStatusRequest;
import com.familyleague.dto.response.LeagueSeasonResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.LeagueSeasonService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Seasons")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class LeagueSeasonController {

    private final LeagueSeasonService seasonService;

    @PostMapping("/admin/leagues/{leagueId}/seasons")
    @Operation(summary = "Create a season for a league",
            description = "Creates a new season under the given league. Season code must be unique within the league.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Season created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "League not found"),
            @ApiResponse(responseCode = "409", description = "Season code already exists in this league")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueSeasonResponse>> createSeason(
            @Parameter(description = "League ID") @PathVariable Long leagueId,
            @Valid @RequestBody CreateSeasonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                com.familyleague.dto.response.ApiResponse.ok("Season created",
                        seasonService.createSeason(leagueId, request)));
    }

    @GetMapping("/api/seasons/{seasonId}")
    @Operation(summary = "Get season by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueSeasonResponse>> getSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                seasonService.getSeasonById(seasonId)));
    }

    @GetMapping("/api/leagues/{leagueId}/seasons")
    @Operation(summary = "List seasons for a league")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seasons returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "League not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<LeagueSeasonResponse>>> getSeasonsByLeague(
            @Parameter(description = "League ID") @PathVariable Long leagueId, Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                seasonService.getSeasonsByLeague(leagueId, pageable)));
    }

    @PatchMapping("/admin/admin/seasons/{seasonId}/status")
    @Operation(summary = "Update season status",
            description = "Transitions season status. Valid values: DRAFT, OPEN, LOCKED, ACTIVE, COMPLETED, CLOSED.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueSeasonResponse>> updateStatus(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @Valid @RequestBody UpdateSeasonStatusRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Status updated",
                seasonService.updateStatus(seasonId, request)));
    }

    @PutMapping("/admin/seasons/{seasonId}/open")
    @Operation(summary = "Open a season for predictions",
            description = "Transitions the season from DRAFT to OPEN, making it available for season-level predictions.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season opened"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season not found"),
            @ApiResponse(responseCode = "409", description = "Season is not in DRAFT status")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueSeasonResponse>> openSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Season opened",
                seasonService.openSeason(seasonId)));
    }

    @PutMapping("/admin/seasons/{seasonId}/close")
    @Operation(summary = "Close a season",
            description = "Marks the season CLOSED. Once closed, no changes are allowed — not even by Admin.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season closed"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> closeSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId) {
        seasonService.closeSeason(seasonId);
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Season closed"));
    }

    @GetMapping("/seasons")
    @Operation(summary = "List all seasons", description = "Returns all seasons across all leagues, paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seasons returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<LeagueSeasonResponse>>> getAllSeasons(
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                seasonService.getAllSeasons(pageable)));
    }

    @PostMapping("/api/seasons/{seasonId}/join")
    @Operation(summary = "Join a season",
            description = "Enrolls the currently authenticated user as an active member of the season.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Joined season successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Season not found"),
            @ApiResponse(responseCode = "409", description = "User is already a member of this season")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> joinSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        seasonService.joinSeason(seasonId, principal.getId());
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Joined season successfully"));
    }
}
