package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateSeasonRequest {

    @NotBlank
    @Size(max = 50)
    private String seasonCode;

    @NotBlank
    @Size(max = 150)
    private String seasonName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Integer predictionLockHours = 4;

    private Integer matchPredictionLockHours = 1;
}
