package com.notesapp.backend.utils.exceptions.user;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BackendException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
