package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.CreateLeagueRequest;
import com.familyleague.dto.response.LeagueResponse;
import com.familyleague.service.LeagueService;
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

@Tag(name = "Leagues")
@RestController
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping("/admin/leagues")
    @Operation(summary = "Create a league")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<LeagueResponse>> createLeague(@Valid @RequestBody CreateLeagueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("League created",
                leagueService.createLeague(request)));
    }

    @GetMapping("/api/leagues/{id}")
    @Operation(summary = "Get league by ID")
    public ResponseEntity<ApiResponse<LeagueResponse>> getLeague(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(leagueService.getLeagueById(id)));
    }

    @GetMapping("/api/leagues")
    @Operation(summary = "List all leagues (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<LeagueResponse>>> getAllLeagues(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(leagueService.getAllLeagues(pageable)));
    }

    @DeleteMapping("/admin/leagues/{id}")
    @Operation(summary = "Delete a league")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.ok(ApiResponse.ok("League deleted"));
    }
}
