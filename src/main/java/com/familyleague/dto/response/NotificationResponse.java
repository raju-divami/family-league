package com.familyleague.dto.response;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String eventType;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;

    private NotificationResponse(Long id, String eventType, String subject, String message,
                                 String status, LocalDateTime scheduledAt, LocalDateTime sentAt) {
        this.id = id;
        this.eventType = eventType;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.sentAt = sentAt;
    }

    public Long getId() { return id; }
    public String getEventType() { return eventType; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public LocalDateTime getSentAt() { return sentAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String eventType;
        private String subject;
        private String message;
        private String status;
        private LocalDateTime scheduledAt;
        private LocalDateTime sentAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder eventType(String eventType) { this.eventType = eventType; return this; }
        public Builder subject(String subject) { this.subject = subject; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder scheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; return this; }
        public Builder sentAt(LocalDateTime sentAt) { this.sentAt = sentAt; return this; }

        public NotificationResponse build() {
            return new NotificationResponse(id, eventType, subject, message, status, scheduledAt, sentAt);
        }
    }
}
