package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String email;
    private String provider;
    private String status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    private UserResponse(Long id, String email, String provider, String status,
                         LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.provider = provider;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getProvider() { return provider; }
    public String getStatus() { return status; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String email;
        private String provider;
        private String status;
        private LocalDateTime lastLoginAt;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder provider(String provider) { this.provider = provider; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder lastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserResponse build() {
            return new UserResponse(id, email, provider, status, lastLoginAt, createdAt);
        }
    }
}
