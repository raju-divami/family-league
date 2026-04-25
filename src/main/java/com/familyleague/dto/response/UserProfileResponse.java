package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String displayName;
    private String avatarName;
    private String profileImageUrl;
}
