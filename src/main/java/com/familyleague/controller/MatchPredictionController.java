package com.familyleague.controller;

import com.familyleague.dto.request.SubmitMatchPredictionRequest;
import com.familyleague.dto.response.MatchPredictionResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.MatchPredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Match Predictions")
@RestController
@RequestMapping("/api/matches/{matchId}/predictions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MatchPredictionController {

    private final MatchPredictionService predictionService;

    @PostMapping
    @Operation(summary = "Submit match prediction",
            description = "Submits the authenticated user's prediction for winner, toss winner and player of the match. Allowed only before the prediction lock time.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prediction submitted"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Match not found"),
            @ApiResponse(responseCode = "409", description = "Prediction already submitted — use PUT to update"),
            @ApiResponse(responseCode = "423", description = "Prediction window is closed")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchPredictionResponse>> submit(
            @Parameter(description = "Match ID") @PathVariable Long matchId,
            @Valid @RequestBody SubmitMatchPredictionRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                com.familyleague.dto.response.ApiResponse.ok("Prediction submitted",
                        predictionService.submitPrediction(matchId, principal.getId(), request)));
    }

    @PutMapping
    @Operation(summary = "Update match prediction",
            description = "Replaces the authenticated user's existing prediction. Allowed only before the prediction lock time.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prediction updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Prediction not found — submit first"),
            @ApiResponse(responseCode = "423", description = "Prediction window is closed")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchPredictionResponse>> update(
            @Parameter(description = "Match ID") @PathVariable Long matchId,
            @Valid @RequestBody SubmitMatchPredictionRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Prediction updated",
                predictionService.updatePrediction(matchId, principal.getId(), request)));
    }

    @GetMapping("/me")
    @Operation(summary = "Get my match prediction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prediction found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "No prediction submitted for this match")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<MatchPredictionResponse>> getMyPrediction(
            @Parameter(description = "Match ID") @PathVariable Long matchId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                predictionService.getMyPrediction(matchId, principal.getId())));
    }

    @GetMapping
    @Operation(summary = "Get all predictions for a match",
            description = "Returns all predictions for the match. **Visible only after the prediction lock time** — returns 403 if the window is still open.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Predictions returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Prediction window is still open — head-to-head not available yet"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<List<MatchPredictionResponse>>> getAllPredictions(
            @Parameter(description = "Match ID") @PathVariable Long matchId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                predictionService.getPredictionsForMatch(matchId, principal.getId())));
    }
}
