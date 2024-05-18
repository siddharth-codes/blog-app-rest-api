package com.example.blogapprestapi.exceptions;

import com.example.blogapprestapi.payloads.ApiResponse;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//use RestControllerAdvice to handle exception for the Rest controller
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodNotFoundExceptionHandler(MethodArgumentNotValidException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> authException(AuthenticationException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), true),HttpStatus.UNAUTHORIZED);
    }
}
