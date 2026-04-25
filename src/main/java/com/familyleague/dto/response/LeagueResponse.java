package com.familyleague.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "League master record")
public class LeagueResponse {

    @Schema(description = "League ID", example = "1")
    private Long id;

    @Schema(description = "Unique league code", example = "IPL")
    private String code;

    @Schema(description = "League name", example = "Indian Premier League")
    private String name;

    @Schema(description = "Sport type", example = "CRICKET")
    private String sportType;

    @Schema(description = "Description", example = "IPL 2026 Season")
    private String description;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}
