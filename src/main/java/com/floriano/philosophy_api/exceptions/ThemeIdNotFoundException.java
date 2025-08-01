package com.floriano.philosophy_api.exceptions;

public class ThemeIdNotFoundException extends RuntimeException {
    public ThemeIdNotFoundException(String message) {
        super(message);
    }
}
