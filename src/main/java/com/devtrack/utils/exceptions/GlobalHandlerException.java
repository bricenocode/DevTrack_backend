package com.devtrack.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> globalExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.valueOf(500)).body(
                CustomError.builder()
                .errorCode(HttpStatus.valueOf(500).value())
                .error(ex.getMessage())
                .timestamp(new Date())
                .build()
        );
    }
}
