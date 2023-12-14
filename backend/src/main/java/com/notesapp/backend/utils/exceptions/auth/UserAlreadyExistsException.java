package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BackendException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
