package com.familyleague.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for updating an existing team.
 * All fields are optional - only provided fields will be updated.
 */
@Getter
@Setter
public class UpdateTeamRequest {

    @Size(max = 150)
    private String name;

    @Size(max = 20)
    private String shortName;

    @Size(max = 500)
    private String logoUrl;
}
