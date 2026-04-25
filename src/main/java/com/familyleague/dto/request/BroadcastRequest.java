package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Admin broadcast message to a selected list of users")
public class BroadcastRequest {

    @NotBlank
    @Schema(description = "Message title / subject", example = "Season Update", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank
    @Schema(description = "Message body", example = "IPL 2026 season predictions are now open!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "Optional event type tag for categorisation", example = "SEASON_OPEN")
    private String eventType;

    @NotEmpty
    @Schema(description = "List of user IDs to broadcast to", example = "[1, 2, 3]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> userIds;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public List<Long> getUserIds() { return userIds; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }
}
