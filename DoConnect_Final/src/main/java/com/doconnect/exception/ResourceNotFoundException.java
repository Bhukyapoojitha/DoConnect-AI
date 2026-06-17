package com.doconnect.exception;

/**
 * ResourceNotFoundException
 * Thrown when requested resource not found
 * Returns 404 HTTP status
 */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
