package com.floriano.philosophy_api.exceptions;

public class WorkIdNotFoundException extends RuntimeException {
    public WorkIdNotFoundException(String message) {
        super(message);
    }
}
