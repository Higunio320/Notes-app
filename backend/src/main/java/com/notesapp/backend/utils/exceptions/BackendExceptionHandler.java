package com.notesapp.backend.utils.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class BackendExceptionHandler {

    @ExceptionHandler({BackendException.class})
    public final ResponseEntity<ExceptionResponse> handleBackendException(BackendException backendException) {

        String message = backendException.getMessage();
        HttpStatus status = backendException.getStatus();
        String exceptionClassName = backendException.getClass().getSimpleName();
        LocalDateTime timeStamp = LocalDateTime.now();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionName(exceptionClassName)
                .message(message)
                .timeStamp(timeStamp)
                .build();

        log.error("Exception occurred with response: {}", exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, status);
    }
}
