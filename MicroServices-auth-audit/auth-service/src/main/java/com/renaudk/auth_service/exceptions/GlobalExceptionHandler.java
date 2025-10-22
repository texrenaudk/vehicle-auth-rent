package com.renaudk.auth_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessaggio(ex.getMessage());
        error.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

}
