package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload for registering a new user account")
public class RegisterRequest {

    @NotBlank
    @Email
    @Schema(description = "Email address (must be unique)", example = "player@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Schema(description = "Password — minimum 8 characters", example = "MyPass@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Size(max = 100)
    @Schema(description = "Display name shown in the league", example = "Raju")
    private String displayName;
}
