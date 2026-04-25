package com.familyleague.controller;

import com.familyleague.dto.request.SubmitSeasonPredictionRequest;
import com.familyleague.dto.response.SeasonPredictionResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.SeasonPredictionService;
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

@Tag(name = "Season Predictions")
@RestController
@RequestMapping("/api/seasons/{seasonId}/predictions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SeasonPredictionController {

    private final SeasonPredictionService predictionService;

    @PostMapping
    @Operation(summary = "Submit season table prediction",
            description = "Submits a full final-standings prediction ranking all N teams from 1st to last. "
                    + "Must be submitted before the season prediction lock (4 hours before first match).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Season prediction submitted"),
            @ApiResponse(responseCode = "400", description = "Validation error — ensure all teams are ranked with no duplicates"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Season not found"),
            @ApiResponse(responseCode = "409", description = "Prediction already submitted — use PUT to update"),
            @ApiResponse(responseCode = "423", description = "Season prediction window is closed")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<SeasonPredictionResponse>> submit(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @Valid @RequestBody SubmitSeasonPredictionRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                com.familyleague.dto.response.ApiResponse.ok("Season prediction submitted",
                        predictionService.submitPrediction(seasonId, principal.getId(), request)));
    }

    @GetMapping("/me")
    @Operation(summary = "Get my season prediction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season prediction found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "No prediction submitted for this season")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<SeasonPredictionResponse>> getMyPrediction(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                predictionService.getMyPrediction(seasonId, principal.getId())));
    }

    @GetMapping
    @Operation(summary = "Get all season predictions",
            description = "Returns all members' season predictions. **Visible only after the prediction lock** — returns 403 while the window is still open.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Predictions returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Prediction window is still open"),
            @ApiResponse(responseCode = "404", description = "Season not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<List<SeasonPredictionResponse>>> getAllPredictions(
            @Parameter(description = "Season ID") @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                predictionService.getPredictionsForSeason(seasonId, principal.getId())));
    }
}
