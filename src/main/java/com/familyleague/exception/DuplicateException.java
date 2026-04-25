package com.familyleague.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * Typically used for unique constraint violations.
 */
public class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String resource, String field, Object value) {
        super(String.format("%s already exists with %s: %s", resource, field, value));
    }
}
