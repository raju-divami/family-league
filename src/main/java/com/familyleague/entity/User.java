package com.familyleague.entity;

import com.familyleague.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}
