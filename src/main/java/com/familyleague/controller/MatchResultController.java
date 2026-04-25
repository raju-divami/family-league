package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.request.PublishResultRequest;
import com.familyleague.dto.response.MatchResultResponse;
import com.familyleague.service.MatchResultService;
import com.familyleague.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Publish match result (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MatchResultResponse>> publishResult(
            @PathVariable Long matchId,
            @Valid @RequestBody PublishResultRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok("Result published",
                matchResultService.publishResult(matchId, request, principal.getId())));
    }

    @GetMapping("/api/matches/{matchId}/result")
    @Operation(summary = "Get result for a match")
    public ResponseEntity<ApiResponse<MatchResultResponse>> getResult(@PathVariable Long matchId) {
        return ResponseEntity.ok(ApiResponse.ok(matchResultService.getResultByMatchId(matchId)));
    }
}
