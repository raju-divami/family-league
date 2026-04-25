package com.familyleague.controller;

import com.familyleague.dto.request.CreateMatchRequest;
import com.familyleague.dto.request.UpdateMatchRequest;
import com.familyleague.dto.response.MatchResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.service.MatchService;
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

@Tag(name = "Matches")
@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/admin/matches")
    @Operation(summary = "Create a match",
            description = "Schedules a match for a season. `predictionLockTime` is computed automatically from `matchPredictionLockHours` on the season.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Match created"),
            @ApiResponse(responseCode = "400", description = "Validation error or overlapping match number"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season or team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResponse>> createMatch(
            @Valid @RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponse.ok("Match created",
                        matchService.createMatch(request.getSeasonId(), request)));
    }

    @PostMapping("/admin/seasons/{seasonId}/matches")
    @Operation(summary = "Create a match in a season",
            description = "Alternative endpoint — season ID from the path takes precedence over the body field.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Match created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season or team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResponse>> createMatchInSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @Valid @RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponse.ok("Match created",
                        matchService.createMatch(seasonId, request)));
    }

    @PutMapping("/admin/matches/{id}")
    @Operation(summary = "Update a match",
            description = "Updates venue or start time. Updating start time also recalculates the prediction lock time.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Match updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResponse>> updateMatch(
            @Parameter(description = "Match ID") @PathVariable Long id,
            @Valid @RequestBody UpdateMatchRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Match updated",
                matchService.updateMatch(id, request)));
    }

    @GetMapping("/api/matches/{id}")
    @Operation(summary = "Get match by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Match found"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResponse>> getMatch(
            @Parameter(description = "Match ID") @PathVariable Long id) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                matchService.getMatchById(id)));
    }

    @GetMapping("/api/matches")
    @Operation(summary = "Get all matches",
            description = "Returns all matches, optionally filtered by `seasonId`. Supports pagination and sorting.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matches returned")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<MatchResponse>>> getAllMatches(
            @Parameter(description = "Filter by season ID (optional)") @RequestParam(required = false) Long seasonId,
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                matchService.getAllMatches(seasonId, pageable)));
    }

    @GetMapping("/api/seasons/{seasonId}/matches")
    @Operation(summary = "Get matches in a season", description = "Returns all matches for a specific season, ordered by start time.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matches returned"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<MatchResponse>>> getMatchesBySeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                matchService.getMatchesBySeason(seasonId, pageable)));
    }
}
