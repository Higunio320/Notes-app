package com.notesapp.backend.exception.handler;

import com.notesapp.backend.exception.NoteException;
import com.notesapp.backend.exception.NoteExceptionResponse;
import com.notesapp.backend.exception.NoteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler({NoteException.class})
    public final ResponseEntity<NoteExceptionResponse> handleNoteException(NoteException noteException) {
        String message = noteException.getMessage();
        String exceptionClassName = noteException.getClass().getSimpleName();
        long timeStamp = System.currentTimeMillis();

        NoteExceptionResponse exceptionResponse = new NoteExceptionResponse(exceptionClassName, message, timeStamp);

        log.error("NoteException occurred: {}", exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
