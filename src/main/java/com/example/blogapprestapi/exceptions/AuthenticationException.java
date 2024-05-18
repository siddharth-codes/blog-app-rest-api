package com.example.blogapprestapi.exceptions;

public class AuthenticationException extends RuntimeException {
    String message;
    public AuthenticationException(String message){
        super(message);
        this.message = message;
    }
}
