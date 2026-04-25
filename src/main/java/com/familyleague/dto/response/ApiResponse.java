package com.familyleague.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API envelope returned by all endpoints")
public class ApiResponse<T> {

    @Schema(description = "true when the operation succeeded", example = "true")
    private final boolean success;

    @Schema(description = "Human-readable status message", example = "League created")
    private final String message;

    @Schema(description = "Response payload (null for operations that return no data)")
    private final T data;

    @Builder.Default
    @Schema(description = "Server timestamp at response time")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static ApiResponse<Void> ok(String message) {
        return ApiResponse.<Void>builder().success(true).message(message).build();
    }

    public static ApiResponse<Void> error(String message) {
        return ApiResponse.<Void>builder().success(false).message(message).build();
    }
}
