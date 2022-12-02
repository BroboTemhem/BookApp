package com.bookapp.app.exeptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GeneralExeption extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomExeption.class)
    public ResponseEntity<Object> handleCustomExeption(CustomExeption ex, WebRequest webRequest) {
        return new ResponseEntity<>(new ApiError(ex.getMessage(), LocalDateTime.now(), NOT_FOUND), NOT_FOUND);
    }

    @ExceptionHandler(NotValidExeption.class)
    public ResponseEntity<Object> handleNotValidArgument(NotValidExeption ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiError(ex.getMessage(),LocalDateTime.now(),BAD_REQUEST),BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(new ApiError("Method Argument Not Valid", LocalDateTime.now(),status),status);
    }


}
