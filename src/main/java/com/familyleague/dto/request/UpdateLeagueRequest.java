package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload for updating an existing league")
public class UpdateLeagueRequest {

    @Size(max = 150)
    @Schema(description = "Display name of the league", example = "Indian Premier League 2026")
    private String name;

    @Size(max = 50)
    @Schema(description = "Sport type", example = "CRICKET")
    private String sportType;

    @Schema(description = "Optional description", example = "Updated season description")
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
