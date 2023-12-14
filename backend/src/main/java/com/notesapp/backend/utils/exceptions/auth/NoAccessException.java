package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NoAccessException extends BackendException {

    @Serial
    private static final long serialVersionUID = 7069224658574221130L;

    private static final String EXCEPTION_MESSAGE = "User with email %s has no access to note with id %d";

    public NoAccessException(long noteId, String userEmail) {
        super(String.format(EXCEPTION_MESSAGE, userEmail, noteId), HttpStatus.FORBIDDEN);
    }
}
