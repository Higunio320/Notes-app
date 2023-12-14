package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAlreadyExistsException extends BackendException {
    @Serial
    private static final long serialVersionUID = 1122246536219631536L;

    private static final String DEFAULT_MESSAGE = "User: %s already exists";

    public UserAlreadyExistsException(String userEmail) {
        super(String.format(DEFAULT_MESSAGE, userEmail), HttpStatus.CONFLICT);
    }
}
