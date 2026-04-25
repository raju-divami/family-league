package com.familyleague.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
