package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload for creating a new league")
public class CreateLeagueRequest {

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Unique league code", example = "IPL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank
    @Size(max = 150)
    @Schema(description = "Full league name", example = "Indian Premier League", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 50)
    @Schema(description = "Sport type", example = "CRICKET", defaultValue = "CRICKET")
    private String sportType = "CRICKET";

    @Schema(description = "Optional description", example = "IPL 2026 Season")
    private String description;
}
