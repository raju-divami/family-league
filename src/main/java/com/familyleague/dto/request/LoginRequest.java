package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Credentials for logging in")
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(description = "Registered email address", example = "admin@familyleague.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Account password", example = "Admin@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
