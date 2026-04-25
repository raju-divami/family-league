package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSeasonStatusRequest {

    @NotBlank
    private String status;
}
