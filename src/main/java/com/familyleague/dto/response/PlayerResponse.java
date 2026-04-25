package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerResponse {

    private Long id;
    private String code;
    private String fullName;
    private String shortName;
    private String country;
}
