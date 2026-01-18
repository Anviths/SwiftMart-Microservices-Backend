package com.swiftmaet.product_service.exception;

import com.swiftmaet.product_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {


        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later."

        );
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {

        ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).status(status.value()).error(status.name()).message(message).build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorResponse> handleCategoryException(CategoryException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
