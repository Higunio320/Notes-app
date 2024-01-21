package com.notesapp.backend.utils.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class BackendExceptionHandler {

    private static final String EXCEPTION_OCCURRED = "Exception occurred with response: {}";

    @ExceptionHandler({BackendException.class})
    public final ResponseEntity<ExceptionResponse> handleBackendException(BackendException backendException) {

        String message = backendException.getMessage();
        HttpStatus status = backendException.getStatus();
        String exceptionClassName = backendException.getClass().getSimpleName();
        LocalDateTime timeStamp = LocalDateTime.now();

        ExceptionResponse exceptionResponse = buildExceptionResponse(exceptionClassName, message, timeStamp);

        log.error(EXCEPTION_OCCURRED, exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<ExceptionResponse> handleConstraintViolationException(
            ConstraintViolationException exception) {

        String exceptionClassName = exception.getClass().getSimpleName();

        String message = getAllViolations(exception);

        LocalDateTime timeStamp = LocalDateTime.now();

        ExceptionResponse exceptionResponse = buildExceptionResponse(exceptionClassName, message, timeStamp);

        log.error(EXCEPTION_OCCURRED, exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse buildExceptionResponse(String exceptionClassName, String message, LocalDateTime timeStamp) {
        return ExceptionResponse.builder()
                .exceptionName(exceptionClassName)
                .message(message)
                .timeStamp(timeStamp)
                .build();
    }

    private String getAllViolations(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .reduce("", (acc, violation) -> String.format("%s%s; ", acc, violation));
    }
}