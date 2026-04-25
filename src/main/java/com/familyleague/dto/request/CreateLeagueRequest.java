package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLeagueRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 50)
    private String sportType = "CRICKET";

    private String description;
}
