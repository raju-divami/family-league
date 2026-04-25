package com.familyleague.exception;

import com.familyleague.dto.response.ApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application using @RestControllerAdvice.
 * Handles all exceptions and returns standardized ApiResponseDto format.
 *
 * Handles:
 * - ResourceNotFoundException (404)
 * - ValidationException (422)
 * - AccessDeniedException (403)
 * - DuplicateException (409)
 * - BusinessException (400 or custom status)
 * - Bean Validation errors (422)
 * - Authentication errors (401)
 * - Generic exceptions (500)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles ResourceNotFoundException - 404 Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles ValidationException - 422 Unprocessable Entity
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleCustomValidation(ValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles AccessDeniedException - 403 Forbidden
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponseDto.error("Access denied"));
    }

    /**
     * Handles DuplicateException - 409 Conflict
     */
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDuplicate(DuplicateException ex) {
        log.warn("Duplicate resource: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles BusinessException - 400 Bad Request or custom status
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBusiness(BusinessException ex) {
        log.warn("Business rule violation: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles ConflictException - 409 Conflict
     * (Legacy handler - consider using DuplicateException for new code)
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleConflict(ConflictException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles PredictionLockedException - 423 Locked
     */
    @ExceptionHandler(PredictionLockedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handlePredictionLocked(PredictionLockedException ex) {
        log.warn("Prediction locked: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.LOCKED)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    /**
     * Handles BadCredentialsException - 401 Unauthorized
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error("Invalid credentials"));
    }

    /**
     * Handles Bean Validation errors (@Valid) - 422 Unprocessable Entity
     * Returns field-level validation errors in a map format
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleBeanValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            errors.put(field, error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponseDto.<Map<String, String>>builder()
                        .success(false)
                        .message("Validation failed")
                        .data(errors)
                        .build());
    }

    /**
     * Handles all uncaught exceptions - 500 Internal Server Error
     * Logs the full stack trace for debugging
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("An unexpected error occurred"));
    }
}
