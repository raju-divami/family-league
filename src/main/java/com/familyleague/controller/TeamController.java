package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.AddTeamToSeasonRequest;
import com.familyleague.dto.request.CreateTeamRequest;
import com.familyleague.dto.request.UpdateTeamRequest;
import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.dto.response.TeamResponse;
import com.familyleague.service.TeamService;
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

@Tag(name = "Teams")
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/admin/teams")
    @Operation(summary = "Create a team")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Team created", teamService.createTeam(request)));
    }

    @PutMapping("/admin/teams/{id}")
    @Operation(summary = "Update a team")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<TeamResponse>> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTeamRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Team updated", teamService.updateTeam(id, request)));
    }

    @DeleteMapping("/admin/teams/{id}")
    @Operation(summary = "Delete a team (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(ApiResponse.ok("Team deleted"));
    }

    @GetMapping("/api/teams/{id}")
    @Operation(summary = "Get team by ID")
    public ResponseEntity<ApiResponse<TeamResponse>> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getTeamById(id)));
    }

    @GetMapping("/api/teams")
    @Operation(summary = "List all teams")
    public ResponseEntity<ApiResponse<PagedResponse<TeamResponse>>> getAllTeams(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getAllTeams(pageable)));
    }

    @PostMapping("/admin/seasons/{seasonId}/teams")
    @Operation(summary = "Add team to season")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<SeasonTeamResponse>> addTeamToSeason(
            @PathVariable Long seasonId,
            @Valid @RequestBody AddTeamToSeasonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Team added to season", teamService.addTeamToSeason(seasonId, request)));
    }

    @GetMapping("/api/seasons/{seasonId}/teams")
    @Operation(summary = "Get teams in a season")
    public ResponseEntity<ApiResponse<List<SeasonTeamResponse>>> getTeamsBySeason(@PathVariable Long seasonId) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getTeamsBySeason(seasonId)));
    }
}
