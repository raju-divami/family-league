package com.familyleague.dto.response;

public class TeamResponse {

    private Long id;
    private String code;
    private String name;
    private String shortName;
    private String logoUrl;

    private TeamResponse(Long id, String code, String name, String shortName, String logoUrl) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public String getLogoUrl() { return logoUrl; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String shortName;
        private String logoUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder code(String code) { this.code = code; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder shortName(String shortName) { this.shortName = shortName; return this; }
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }

        public TeamResponse build() { return new TeamResponse(id, code, name, shortName, logoUrl); }
    }
}
