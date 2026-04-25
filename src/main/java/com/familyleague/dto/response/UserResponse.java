package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String provider;
    private String status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
}
