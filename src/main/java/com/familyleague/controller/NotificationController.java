package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.BroadcastRequest;
import com.familyleague.dto.response.NotificationResponse;
import com.familyleague.service.NotificationService;
import com.familyleague.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get my notifications")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getMyNotifications(
            @AuthenticationPrincipal UserPrincipal principal, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(
                notificationService.getMyNotifications(principal.getId(), pageable)));
    }

    @PostMapping("/broadcast")
    @Operation(summary = "Admin broadcast to selected users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> broadcast(
            @Valid @RequestBody BroadcastRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        notificationService.broadcast(request, principal.getId());
        return ResponseEntity.ok(ApiResponse.ok("Broadcast sent successfully"));
    }
}
