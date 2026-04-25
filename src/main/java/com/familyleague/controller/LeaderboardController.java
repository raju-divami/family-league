package com.familyleague.controller;

import com.familyleague.dto.response.LeaderboardResponse;
import com.familyleague.dto.response.PointTransactionResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Leaderboard")
@RestController
@RequestMapping("/api/seasons/{seasonId}/leaderboard")
@SecurityRequirement(name = "bearerAuth")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    @Operation(summary = "Get leaderboard for a season",
            description = "Returns all season members ranked by total points. Rankings are recalculated asynchronously after each result is published.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leaderboard returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<List<LeaderboardResponse>>> getLeaderboard(
            @Parameter(description = "Season ID") @PathVariable Long seasonId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                leaderboardService.getLeaderboard(seasonId)));
    }

    @GetMapping("/my-points")
    @Operation(summary = "Get my point transaction history",
            description = "Returns the full history of points earned by the authenticated user in this season, one entry per scoring event.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Point history returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<List<PointTransactionResponse>>> getMyPoints(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                leaderboardService.getPointHistory(seasonId, principal.getId())));
    }
}
