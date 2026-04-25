package com.familyleague.controller;

import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.security.UserPrincipal;
import com.familyleague.dto.request.UpdateProfileRequest;
import com.familyleague.dto.response.UserProfileResponse;
import com.familyleague.dto.response.UserResponse;
import com.familyleague.service.UserService;
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

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(principal.getId())));
    }

    @PutMapping("/me/profile")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Profile updated",
                userService.updateProfile(principal.getId(), request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getUserById(id)));
    }

    @GetMapping
    @Operation(summary = "List all users (paginated)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getAllUsers(pageable)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.ok("User deactivated"));
    }
}
