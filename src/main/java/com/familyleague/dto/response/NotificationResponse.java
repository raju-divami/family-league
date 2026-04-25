package com.familyleague.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {

    private Long id;
    private String eventType;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
}
