package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LeagueResponse {

    private Long id;
    private String code;
    private String name;
    private String sportType;
    private String description;
    private LocalDateTime createdAt;
}
