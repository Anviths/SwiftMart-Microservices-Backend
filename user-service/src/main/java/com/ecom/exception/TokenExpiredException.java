package com.ecom.exception;

public class TokenExpiredException extends ApiException{
    public TokenExpiredException() {
        super("token expired");
    }
}
