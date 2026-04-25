package com.familyleague.controller;

import com.familyleague.dto.request.AddTeamToSeasonRequest;
import com.familyleague.dto.request.CreateTeamRequest;
import com.familyleague.dto.request.UpdateTeamRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.dto.response.TeamResponse;
import com.familyleague.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teams")
@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/admin/teams")
    @Operation(summary = "Create a team", description = "Creates a global team record. Team code must be unique.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Team created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Team code already exists")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<TeamResponse>> createTeam(
            @Valid @RequestBody CreateTeamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponseDto.ok("Team created",
                        teamService.createTeam(request)));
    }

    @PutMapping("/admin/teams/{id}")
    @Operation(summary = "Update a team")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<TeamResponse>> updateTeam(
            @Parameter(description = "Team ID") @PathVariable Long id,
            @Valid @RequestBody UpdateTeamRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok("Team updated",
                teamService.updateTeam(id, request)));
    }

    @DeleteMapping("/admin/teams/{id}")
    @Operation(summary = "Delete a team", description = "Soft-deletes the team (sets deleted=true).")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<Void>> deleteTeam(
            @Parameter(description = "Team ID") @PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok("Team deleted"));
    }

    @GetMapping("/api/teams/{id}")
    @Operation(summary = "Get team by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team found"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<TeamResponse>> getTeam(
            @Parameter(description = "Team ID") @PathVariable Long id) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                teamService.getTeamById(id)));
    }

    @GetMapping("/api/teams")
    @Operation(summary = "List all teams", description = "Returns all non-deleted teams, paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teams returned")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<PagedResponse<TeamResponse>>> getAllTeams(
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                teamService.getAllTeams(pageable)));
    }

    @PostMapping("/admin/seasons/{seasonId}/teams")
    @Operation(summary = "Add team to season",
            description = "Assigns an existing global team to the given season, optionally with a seed rank.")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Team added to season"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Season or team not found"),
            @ApiResponse(responseCode = "409", description = "Team already in this season")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<SeasonTeamResponse>> addTeamToSeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @Valid @RequestBody AddTeamToSeasonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponseDto.ok("Team added to season",
                        teamService.addTeamToSeason(seasonId, request)));
    }

    @GetMapping("/api/seasons/{seasonId}/teams")
    @Operation(summary = "Get teams in a season")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teams returned"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<List<SeasonTeamResponse>>> getTeamsBySeason(
            @Parameter(description = "Season ID") @PathVariable Long seasonId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                teamService.getTeamsBySeason(seasonId)));
    }
}
