package com.familyleague.dto.response;

public class PlayerResponse {

    private Long id;
    private String code;
    private String fullName;
    private String shortName;
    private String country;

    private PlayerResponse(Long id, String code, String fullName, String shortName, String country) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.shortName = shortName;
        this.country = country;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getFullName() { return fullName; }
    public String getShortName() { return shortName; }
    public String getCountry() { return country; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String code;
        private String fullName;
        private String shortName;
        private String country;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder code(String code) { this.code = code; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder shortName(String shortName) { this.shortName = shortName; return this; }
        public Builder country(String country) { this.country = country; return this; }

        public PlayerResponse build() { return new PlayerResponse(id, code, fullName, shortName, country); }
    }
}
