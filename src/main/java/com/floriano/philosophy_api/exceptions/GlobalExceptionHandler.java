package com.floriano.philosophy_api.exceptions;

import com.floriano.philosophy_api.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        logger.error("Erro interno no servidor", ex);
        return new ResponseEntity<>(new ApiResponse<>(false, "Erro interno do servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhilosopherAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handlePhilosopherAlreadyExists(PhilosopherAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhilosopherIdNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlePhilosopherIdNotFound(PhilosopherAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCountryNotFound(CountryNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SchoolOfThoghtNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSchoolOfThoughtNotFound(SchoolOfThoghtNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InfluenceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleInfluenceNotFound(InfluenceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
