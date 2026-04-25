package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateMatchRequest;
import com.familyleague.dto.request.UpdateMatchRequest;
import com.familyleague.dto.response.MatchResponse;
import com.familyleague.service.MatchService;
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

@Tag(name = "Matches")
@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/admin/matches")
    @Operation(summary = "Create a match")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<MatchResponse>> createMatch(
            @Valid @RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Match created", matchService.createMatch(request.getSeasonId(), request)));
    }

    @PostMapping("/admin/seasons/{seasonId}/matches")
    @Operation(summary = "Create a match in a season (alternative endpoint)")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<MatchResponse>> createMatchInSeason(
            @PathVariable Long seasonId,
            @Valid @RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Match created", matchService.createMatch(seasonId, request)));
    }

    @PutMapping("/admin/matches/{id}")
    @Operation(summary = "Update a match")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<MatchResponse>> updateMatch(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMatchRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Match updated", matchService.updateMatch(id, request)));
    }

    @GetMapping("/api/matches/{id}")
    @Operation(summary = "Get match by ID")
    public ResponseEntity<ApiResponse<MatchResponse>> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(matchService.getMatchById(id)));
    }

    @GetMapping("/api/matches")
    @Operation(summary = "Get all matches (optionally filtered by seasonId)")
    public ResponseEntity<ApiResponse<PagedResponse<MatchResponse>>> getAllMatches(
            @RequestParam(required = false) Long seasonId,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(matchService.getAllMatches(seasonId, pageable)));
    }

    @GetMapping("/api/seasons/{seasonId}/matches")
    @Operation(summary = "Get matches in a season")
    public ResponseEntity<ApiResponse<PagedResponse<MatchResponse>>> getMatchesBySeason(
            @PathVariable Long seasonId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(matchService.getMatchesBySeason(seasonId, pageable)));
    }
}
