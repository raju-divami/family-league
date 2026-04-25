package com.familyleague.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

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

    private LeagueResponse(Long id, String code, String name, String sportType, String description, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sportType = sportType;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getSportType() { return sportType; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String sportType;
        private String description;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder code(String code) { this.code = code; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder sportType(String sportType) { this.sportType = sportType; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public LeagueResponse build() {
            return new LeagueResponse(id, code, name, sportType, description, createdAt);
        }
    }
}
