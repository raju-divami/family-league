package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.LeaderboardResponse;
import com.familyleague.dto.response.PointTransactionResponse;
import com.familyleague.service.LeaderboardService;
import com.familyleague.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Leaderboard")
@RestController
@RequestMapping("/api/seasons/{seasonId}/leaderboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    @Operation(summary = "Get leaderboard for a season")
    public ResponseEntity<ApiResponse<List<LeaderboardResponse>>> getLeaderboard(@PathVariable Long seasonId) {
        return ResponseEntity.ok(ApiResponse.ok(leaderboardService.getLeaderboard(seasonId)));
    }

    @GetMapping("/my-points")
    @Operation(summary = "Get my point transaction history")
    public ResponseEntity<ApiResponse<List<PointTransactionResponse>>> getMyPoints(
            @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(
                leaderboardService.getPointHistory(seasonId, principal.getId())));
    }
}
