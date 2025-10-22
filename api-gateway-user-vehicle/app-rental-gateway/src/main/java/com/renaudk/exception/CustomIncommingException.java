package com.renaudk.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data

@AllArgsConstructor
public class CustomIncommingException extends RuntimeException {
    private final HttpStatus originalStatus;
    private final String originalBody;

    public CustomIncommingException(HttpStatus originalStatus, String originalBody, String message){
        super(message);
        this.originalStatus = originalStatus;
        this.originalBody = originalBody;
    }

}
