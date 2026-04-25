package com.familyleague.exception;

/**
 * Exception thrown when custom validation rules are violated.
 * For bean validation (@Valid), use MethodArgumentNotValidException which is handled separately.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String field, String message) {
        super(String.format("Validation failed for field '%s': %s", field, message));
    }
}
