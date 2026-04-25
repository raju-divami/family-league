package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamResponse {

    private Long id;
    private String code;
    private String name;
    private String shortName;
    private String logoUrl;
}
