package com.familyleague.controller;

import com.familyleague.dto.request.UpdateProfileRequest;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.response.UserProfileResponse;
import com.familyleague.dto.response.UserResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                userService.getProfile(principal.getId())));
    }

    @PutMapping("/me/profile")
    @Operation(summary = "Update current user profile",
            description = "Updates avatar name and/or profile image URL for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<UserProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Profile updated",
                userService.updateProfile(principal.getId(), request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Admin-only — returns full user record including role info.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<UserResponse>> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                userService.getUserById(id)));
    }

    @GetMapping
    @Operation(summary = "List all users", description = "Admin-only — paginated list of all registered users.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<PagedResponse<UserResponse>>> getAllUsers(
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok(
                userService.getAllUsers(pageable)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate user",
            description = "Admin-only — sets user status to INACTIVE. The user can no longer log in.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deactivated"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<Void>> deactivateUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("User deactivated"));
    }
}
