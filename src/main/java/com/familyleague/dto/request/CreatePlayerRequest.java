package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
