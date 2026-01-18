package com.ecom.exception;

public class UserNotFoundException extends ApiException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
