package com.familyleague.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    @Size(max = 100)
    private String displayName;

    @Size(max = 100)
    private String avatarName;

    @Size(max = 500)
    private String profileImageUrl;
}
