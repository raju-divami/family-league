package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePlayerRequest {

    @Size(max = 50)
    private String code;

    @NotBlank
    @Size(max = 150)
    private String fullName;

    @Size(max = 100)
    private String shortName;

    @Size(max = 100)
    private String country;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
