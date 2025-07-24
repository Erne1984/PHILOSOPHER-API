package com.floriano.philosophy_api.exceptions;

public class PhilosopherAlreadyExistsException extends RuntimeException {
    public PhilosopherAlreadyExistsException(String message) {
        super(message);
    }
}
