package com.familyleague.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

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

    private AuthResponse(String accessToken, String refreshToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresIn() { return expiresIn; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private long expiresIn;

        public Builder accessToken(String accessToken) { this.accessToken = accessToken; return this; }
        public Builder refreshToken(String refreshToken) { this.refreshToken = refreshToken; return this; }
        public Builder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public Builder expiresIn(long expiresIn) { this.expiresIn = expiresIn; return this; }

        public AuthResponse build() { return new AuthResponse(accessToken, refreshToken, tokenType, expiresIn); }
    }

    public static AuthResponse of(String accessToken, String refreshToken, long expiresIn) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();
    }
}
