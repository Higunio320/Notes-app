package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidJWTException extends BackendException {
    @Serial
    private static final long serialVersionUID = 2413241390391517217L;

    public InvalidJWTException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public InvalidJWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
