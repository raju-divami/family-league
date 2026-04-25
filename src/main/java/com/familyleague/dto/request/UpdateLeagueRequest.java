package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
