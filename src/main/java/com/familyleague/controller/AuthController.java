package com.familyleague.controller;

import com.familyleague.dto.request.LoginRequest;
import com.familyleague.dto.request.RegisterRequest;
import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.AuthResponse;
import com.familyleague.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login with email and password",
            description = "Authenticates the user and returns a JWT access token and refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful — tokens returned"),
            @ApiResponse(responseCode = "400", description = "Validation error — missing or malformed fields"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Login successful",
                authService.login(request)));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user account",
            description = "Creates a new USER-role account. Returns tokens so the user is immediately authenticated.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.familyleague.dto.response.ApiResponse.ok("Registration successful",
                        authService.register(request)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token",
            description = "Issues a new access token using a valid refresh token. The refresh token is passed as a query parameter.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New access token issued"),
            @ApiResponse(responseCode = "401", description = "Refresh token invalid or expired")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponse<AuthResponse>> refreshToken(
            @RequestParam String refreshToken) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponse.ok("Token refreshed",
                authService.refreshToken(refreshToken)));
    }
}
