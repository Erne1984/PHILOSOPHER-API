package com.floriano.philosophy_api.exceptions;

public class PhilosopherIdNotFoundException extends RuntimeException {
    public PhilosopherIdNotFoundException(String message) {
        super(message);
    }
}
