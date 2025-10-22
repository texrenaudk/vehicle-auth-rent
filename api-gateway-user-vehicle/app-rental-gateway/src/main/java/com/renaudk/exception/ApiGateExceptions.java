package com.renaudk.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

import static com.renaudk.exception.ErrorResponse.*;

@RestControllerAdvice
public class ApiGateExceptions {

    @ExceptionHandler(CustomIncommingException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleServiceCommunicationException(
            CustomIncommingException ex, WebRequest request) {

        HttpStatus status = ex.getOriginalStatus();

        String requestDescription = request.getDescription(false);

        // Si c'est une erreur du service amont, on utilise son statut
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                // On pourrait analyser originalMessage pour plus de détails,
                // mais pour la simplification, on utilise un message générique + le statut
                String.format("Erreur du service amont (%d). Détails: %s", status.value(), ex.getMessage()),

                requestDescription
        );

        return Mono.just(new ResponseEntity<>(errorResponse, ex.getOriginalStatus()));
    }

    // Vous pouvez ajouter d'autres gestionnaires comme handleNotFoundExceptions, etc.

    /**
     * Gère toutes les autres exceptions non prévues (ex: connexion au proxy coupée, timeout).
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAllExceptions(
            Exception ex,
            WebRequest request) { // WebRequest est le paramètre compatible MVC

        String path = getPathFromRequest(request);

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),

                ex.getMessage() != null ? ex.getMessage() : "Une erreur interne inconnue est survenue.",
                path
        );

        return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR));
    }
    private String getPathFromRequest(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
            return servletRequest.getRequestURI();
        }
        // Fallback si le type n'est pas celui attendu (bien que peu probable en MVC)
        return "N/A";
    }
}
