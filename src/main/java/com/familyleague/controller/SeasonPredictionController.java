package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.request.SubmitSeasonPredictionRequest;
import com.familyleague.dto.response.SeasonPredictionResponse;
import com.familyleague.service.SeasonPredictionService;
import com.familyleague.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Submit season table prediction")
    public ResponseEntity<ApiResponse<SeasonPredictionResponse>> submit(
            @PathVariable Long seasonId,
            @Valid @RequestBody SubmitSeasonPredictionRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Season prediction submitted",
                predictionService.submitPrediction(seasonId, principal.getId(), request)));
    }

    @GetMapping("/me")
    @Operation(summary = "Get my season prediction")
    public ResponseEntity<ApiResponse<SeasonPredictionResponse>> getMyPrediction(
            @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(predictionService.getMyPrediction(seasonId, principal.getId())));
    }

    @GetMapping
    @Operation(summary = "Get all season predictions (visible after lock)")
    public ResponseEntity<ApiResponse<List<SeasonPredictionResponse>>> getAllPredictions(
            @PathVariable Long seasonId,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(
                predictionService.getPredictionsForSeason(seasonId, principal.getId())));
    }
}
