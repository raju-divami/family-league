package com.familyleague.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BroadcastRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    private String eventType;

    @NotEmpty
    private List<Long> userIds;
}
