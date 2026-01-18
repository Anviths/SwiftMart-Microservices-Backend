package com.ecom.exception;

public class InvalidCredentialsException extends ApiException{
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
