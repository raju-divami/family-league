package com.familyleague.controller;

import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.request.UpdateLeagueRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.LeagueResponse;
import com.familyleague.service.LeagueService;
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

@Tag(name = "Leagues")
@RestController
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping("/admin/leagues")
    @Operation(summary = "Create a league", description = "Creates a new league master record. League code must be unique.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "League created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "League code already exists")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueResponse>> createLeague(
            @Valid @RequestBody CreateLeagueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                com.familyleague.dto.response.ApiResponse.ok("League created",
                        leagueService.createLeague(request)));
    }

    @GetMapping("/api/leagues/{id}")
    @Operation(summary = "Get league by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "League found"),
            @ApiResponse(responseCode = "404", description = "League not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueResponse>> getLeague(
            @Parameter(description = "League ID") @PathVariable Long id) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                leagueService.getLeagueById(id)));
    }

    @GetMapping("/api/leagues")
    @Operation(summary = "List all leagues", description = "Returns a paginated list of all non-deleted leagues.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leagues returned")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<LeagueResponse>>> getAllLeagues(
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                leagueService.getAllLeagues(pageable)));
    }

    @PutMapping("/admin/leagues/{id}")
    @Operation(summary = "Update a league", description = "Updates name, sport type or description. Only provided fields are changed.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "League updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "League not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<LeagueResponse>> updateLeague(
            @Parameter(description = "League ID") @PathVariable Long id,
            @Valid @RequestBody UpdateLeagueRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("League updated",
                leagueService.updateLeague(id, request)));
    }

    @DeleteMapping("/admin/leagues/{id}")
    @Operation(summary = "Delete a league", description = "Soft-deletes the league (sets deleted=true).")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "League deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "League not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> deleteLeague(
            @Parameter(description = "League ID") @PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("League deleted"));
    }
}
