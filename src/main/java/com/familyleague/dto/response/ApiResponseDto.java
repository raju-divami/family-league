package com.familyleague.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API envelope returned by all endpoints")
public class ApiResponseDto<T> {

    @Schema(description = "true when the operation succeeded", example = "true")
    private final boolean success;

    @Schema(description = "Human-readable status message", example = "League created")
    private final String message;

    @Schema(description = "Response payload (null for operations that return no data)")
    private final T data;

    @Schema(description = "Server timestamp at response time")
    private final LocalDateTime timestamp;

    private ApiResponseDto(boolean success, String message, T data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public static <T> Builder<T> builder() { return new Builder<>(); }

    public static class Builder<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp = LocalDateTime.now();

        public Builder<T> success(boolean success) { this.success = success; return this; }
        public Builder<T> message(String message) { this.message = message; return this; }
        public Builder<T> data(T data) { this.data = data; return this; }
        public Builder<T> timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public ApiResponseDto<T> build() { return new ApiResponseDto<>(success, message, data, timestamp); }
    }

    public static <T> ApiResponseDto<T> ok(T data) {
        return ApiResponseDto.<T>builder().success(true).data(data).build();
    }

    public static <T> ApiResponseDto<T> ok(String message, T data) {
        return ApiResponseDto.<T>builder().success(true).message(message).data(data).build();
    }

    public static ApiResponseDto<Void> ok(String message) {
        return ApiResponseDto.<Void>builder().success(true).message(message).build();
    }

    public static ApiResponseDto<Void> error(String message) {
        return ApiResponseDto.<Void>builder().success(false).message(message).build();
    }
}
