package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "code", length = 50, unique = true)
    private String code;

    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "country", length = 100)
    private String country;

    public Player() {
    }

    public Player(String code, String fullName, String shortName, String country) {
        this.code = code;
        this.fullName = fullName;
        this.shortName = shortName;
        this.country = country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String fullName;
        private String shortName;
        private String country;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Player build() {
            Player obj = new Player();
            obj.code = this.code;
            obj.fullName = this.fullName;
            obj.shortName = this.shortName;
            obj.country = this.country;
            return obj;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
