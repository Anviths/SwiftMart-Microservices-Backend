package com.swiftmart.inventory_service.exception;

import com.swiftmart.inventory_service.dto.ErrorResponse;
import com.swiftmart.inventory_service.dto.InventoryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {



    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {

        ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).status(status.value()).error(status.name()).message(message).build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<ErrorResponse> handleInvetoryException(InventoryException ex) {


        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage()

        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {


        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later."

        );
    }
}
