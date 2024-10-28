package com.kaki.doctrack.building.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LocationNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(LocationNotFoundException ex) {
        logger.error("Handling Location exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAllExceptions(Exception ex) {
        logger.error("Handling unexpected exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(AreaNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(AreaNotFoundException ex) {
        logger.error("Handling Area exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(AreaContainsChildrenException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(AreaContainsChildrenException ex) {
        logger.error("Handling Area exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(DuplicateAreaTypeNameException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(DuplicateAreaTypeNameException ex) {
        logger.error("Handling Area exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(AreaTypeNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(AreaTypeNotFoundException ex) {
        logger.error("Handling Area exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(SearchTermException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleJwtException(SearchTermException ex) {
        logger.error("Handling SearchTerm exception: {}", ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

}

