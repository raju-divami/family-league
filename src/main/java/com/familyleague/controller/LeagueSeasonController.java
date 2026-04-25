package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateSeasonRequest;
import com.familyleague.dto.request.UpdateSeasonStatusRequest;
import com.familyleague.dto.response.LeagueSeasonResponse;
import com.familyleague.service.LeagueSeasonService;
import com.familyleague.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Create a season for a league")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeagueSeasonResponse>> createSeason(
            @PathVariable Long leagueId,
            @Valid @RequestBody CreateSeasonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Season created",
                seasonService.createSeason(leagueId, request)));
    }

    @GetMapping("/api/seasons/{seasonId}")
    @Operation(summary = "Get season by ID")
    public ResponseEntity<ApiResponse<LeagueSeasonResponse>> getSeason(@PathVariable Long seasonId) {
        return ResponseEntity.ok(ApiResponse.ok(seasonService.getSeasonById(seasonId)));
    }

    @GetMapping("/api/leagues/{leagueId}/seasons")
    @Operation(summary = "List seasons for a league")
    public ResponseEntity<ApiResponse<PagedResponse<LeagueSeasonResponse>>> getSeasonsByLeague(
            @PathVariable Long leagueId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(seasonService.getSeasonsByLeague(leagueId, pageable)));
    }

    @PatchMapping("/admin/admin/seasons/{seasonId}/status")
    @Operation(summary = "Update season status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeagueSeasonResponse>> updateStatus(
            @PathVariable Long seasonId,
            @Valid @RequestBody UpdateSeasonStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Status updated",
                seasonService.updateStatus(seasonId, request)));
    }

    @PutMapping("/admin/seasons/{seasonId}/open")
    @Operation(summary = "Open a season for predictions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeagueSeasonResponse>> openSeason(@PathVariable Long seasonId) {
        return ResponseEntity.ok(ApiResponse.ok("Season opened",
                seasonService.openSeason(seasonId)));
    }

    @PutMapping("/admin/seasons/{seasonId}/close")
    @Operation(summary = "Close a season")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> closeSeason(@PathVariable Long seasonId) {
        seasonService.closeSeason(seasonId);
        return ResponseEntity.ok(ApiResponse.ok("Season closed"));
    }

    @GetMapping("/seasons")
    @Operation(summary = "List all seasons (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<LeagueSeasonResponse>>> getAllSeasons(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(seasonService.getAllSeasons(pageable)));
    }

    @PostMapping("/api/seasons/{seasonId}/join")
    @Operation(summary = "Join a season")
    public ResponseEntity<ApiResponse<Void>> joinSeason(
            @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        seasonService.joinSeason(seasonId, principal.getId());
        return ResponseEntity.ok(ApiResponse.ok("Joined season successfully"));
    }
}
