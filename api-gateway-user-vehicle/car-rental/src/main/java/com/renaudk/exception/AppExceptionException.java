package com.renaudk.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AppExceptionException {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handle(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404, e.getMessage()));
    }
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(VehicleNotFoundException e){

        System.out.println("VehicleNotFoundException interceptée !");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404, e.getMessage()));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorMessage> handle(IllegalAccessException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(400, e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handle(MethodArgumentNotValidException e){
        var errors = e.getAllErrors();
        if(errors != null && !errors.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(400, errors.get(0).getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(400, "Bad Request"));
    }
}
