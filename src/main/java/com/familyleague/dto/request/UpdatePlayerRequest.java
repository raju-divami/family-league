package com.familyleague.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for updating an existing player.
 * All fields are optional - only provided fields will be updated.
 */
@Getter
@Setter
public class UpdatePlayerRequest {

    @Size(max = 150)
    private String fullName;

    @Size(max = 100)
    private String shortName;

    @Size(max = 100)
    private String country;
}
