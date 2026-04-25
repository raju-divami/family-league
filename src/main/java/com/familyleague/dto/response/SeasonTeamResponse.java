package com.familyleague.dto.response;

public class SeasonTeamResponse {

    private Long id;
    private Long seasonId;
    private Long teamId;
    private String teamName;
    private String teamCode;
    private Integer seedRank;

    private SeasonTeamResponse(Long id, Long seasonId, Long teamId, String teamName, String teamCode, Integer seedRank) {
        this.id = id;
        this.seasonId = seasonId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.seedRank = seedRank;
    }

    public Long getId() { return id; }
    public Long getSeasonId() { return seasonId; }
    public Long getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public String getTeamCode() { return teamCode; }
    public Integer getSeedRank() { return seedRank; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long seasonId;
        private Long teamId;
        private String teamName;
        private String teamCode;
        private Integer seedRank;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder seasonId(Long seasonId) { this.seasonId = seasonId; return this; }
        public Builder teamId(Long teamId) { this.teamId = teamId; return this; }
        public Builder teamName(String teamName) { this.teamName = teamName; return this; }
        public Builder teamCode(String teamCode) { this.teamCode = teamCode; return this; }
        public Builder seedRank(Integer seedRank) { this.seedRank = seedRank; return this; }

        public SeasonTeamResponse build() {
            return new SeasonTeamResponse(id, seasonId, teamId, teamName, teamCode, seedRank);
        }
    }
}
