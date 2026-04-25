package com.familyleague.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "JWT token response returned after login or registration")
public class AuthResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private final String accessToken;

    @Schema(description = "JWT refresh token — use at POST /api/auth/refresh before access token expires", example = "eyJhbGciOiJIUzI1NiJ9...")
    private final String refreshToken;

    @Schema(description = "Token type — always Bearer", example = "Bearer")
    private final String tokenType;

    @Schema(description = "Access token lifetime in milliseconds", example = "86400000")
    private final long expiresIn;

    public static AuthResponse of(String accessToken, String refreshToken, long expiresIn) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();
    }
}
