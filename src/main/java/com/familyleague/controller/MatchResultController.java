package com.familyleague.controller;

import com.familyleague.dto.request.PublishResultRequest;
import com.familyleague.dto.response.MatchResultResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.MatchResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Match Results")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MatchResultController {

    private final MatchResultService matchResultService;

    @PostMapping("/admin/matches/{matchId}/result")
    @Operation(summary = "Publish match result",
            description = "Records the official result for a match. Triggers async leaderboard recalculation and sends admin email notification.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Result published — leaderboard recalculation queued"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Match not found"),
            @ApiResponse(responseCode = "409", description = "Result already published for this match")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResultResponse>> publishResult(
            @Parameter(description = "Match ID") @PathVariable Long matchId,
            @Valid @RequestBody PublishResultRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Result published",
                matchResultService.publishResult(matchId, request, principal.getId())));
    }

    @GetMapping("/api/matches/{matchId}/result")
    @Operation(summary = "Get result for a match")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Result found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Match or result not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchResultResponse>> getResult(
            @Parameter(description = "Match ID") @PathVariable Long matchId) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                matchResultService.getResultByMatchId(matchId)));
    }
}
