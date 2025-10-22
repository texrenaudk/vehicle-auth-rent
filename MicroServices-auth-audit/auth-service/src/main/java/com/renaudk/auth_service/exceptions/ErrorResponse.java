package com.renaudk.auth_service.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse {
    private int code;
    private LocalDateTime date;
    private String messaggio;
}
