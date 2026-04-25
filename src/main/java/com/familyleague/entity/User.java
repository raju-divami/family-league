package com.familyleague.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "provider", length = 30)
    private String provider;

    @Column(name = "provider_user_id", length = 150)
    private String providerUserId;

    @Column(name = "status", length = 30, nullable = false)
    private String status = "ACTIVE";

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public User() {
    }

    public User(String email, String passwordHash, String provider, String providerUserId, String status, LocalDateTime lastLoginAt) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String passwordHash;
        private String provider;
        private String providerUserId;
        private String status = "ACTIVE";
        private LocalDateTime lastLoginAt;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder providerUserId(String providerUserId) {
            this.providerUserId = providerUserId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }

        public User build() {
            User obj = new User();
            obj.email = this.email;
            obj.passwordHash = this.passwordHash;
            obj.provider = this.provider;
            obj.providerUserId = this.providerUserId;
            obj.status = this.status;
            obj.lastLoginAt = this.lastLoginAt;
            return obj;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
