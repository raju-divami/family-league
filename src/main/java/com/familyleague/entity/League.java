package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leagues")
public class League extends BaseEntity {

    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "sport_type", length = 50)
    private String sportType = "CRICKET";

    @Column(name = "description", columnDefinition = "text")
    private String description;

    public League() {
    }

    public League(String code, String name, String sportType, String description) {
        this.code = code;
        this.name = name;
        this.sportType = sportType;
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String name;
        private String sportType = "CRICKET";
        private String description;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sportType(String sportType) {
            this.sportType = sportType;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public League build() {
            League obj = new League();
            obj.code = this.code;
            obj.name = this.name;
            obj.sportType = this.sportType;
            obj.description = this.description;
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

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
