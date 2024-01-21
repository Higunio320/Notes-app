package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends BackendException {
    public WrongPasswordException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
