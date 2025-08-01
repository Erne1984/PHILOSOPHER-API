package com.floriano.philosophy_api.exceptions;

public class QuoteIdNotFoundException extends RuntimeException {
    public QuoteIdNotFoundException(String message) {
        super(message);
    }
}
