package com.notesapp.backend.exception.handler;

import com.notesapp.backend.exception.NoteExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public final ResponseEntity<NoteExceptionResponse> handleRuntimeException(RuntimeException runtimeException) {
        String message = runtimeException.getMessage();
        String exceptionClassName = runtimeException.getClass().getSimpleName();
        long timeStamp = System.currentTimeMillis();

        NoteExceptionResponse exceptionResponse = new NoteExceptionResponse(exceptionClassName, message, timeStamp);

        log.error("RuntimeException occurred: {}", exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
