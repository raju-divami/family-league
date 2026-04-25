package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "short_name", length = 20)
    private String shortName;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    public Team() {
    }

    public Team(String code, String name, String shortName, String logoUrl) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String name;
        private String shortName;
        private String logoUrl;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Team build() {
            Team obj = new Team();
            obj.code = this.code;
            obj.name = this.name;
            obj.shortName = this.shortName;
            obj.logoUrl = this.logoUrl;
            return obj;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
