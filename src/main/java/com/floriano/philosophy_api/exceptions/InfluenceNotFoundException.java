package com.floriano.philosophy_api.exceptions;

public class InfluenceNotFoundException extends RuntimeException {
    public InfluenceNotFoundException(String message) {
        super(message);
    }
}
