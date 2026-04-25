package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
