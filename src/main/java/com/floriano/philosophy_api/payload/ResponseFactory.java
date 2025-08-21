package com.floriano.philosophy_api.payload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(true, message, data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.NOT_FOUND);
    }
}