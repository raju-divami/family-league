package com.familyleague.controller;

import com.familyleague.dto.request.BroadcastRequest;
import com.familyleague.dto.response.NotificationResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notifications")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/me")
    @Operation(summary = "Get my notifications",
            description = "Returns all notifications addressed to the authenticated user, paginated and ordered newest-first.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notifications returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<NotificationResponse>>> getMyNotifications(
            @AuthenticationPrincipal UserPrincipal principal, Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                notificationService.getMyNotifications(principal.getId(), pageable)));
    }

    @PostMapping("/broadcast")
    @Operation(summary = "Broadcast a message to selected users",
            description = "Admin-only — sends a custom message to a specified list of user IDs. "
                    + "The message is persisted in the notification store and emailed asynchronously.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Broadcast sent"),
            @ApiResponse(responseCode = "400", description = "Validation error — title, message and userIds are required"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> broadcast(
            @Valid @RequestBody BroadcastRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        notificationService.broadcast(request, principal.getId());
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Broadcast sent successfully"));
    }
}
