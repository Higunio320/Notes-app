package com.notesapp.backend.utils.exceptions.user;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserNotFoundException extends BackendException {
    @Serial
    private static final long serialVersionUID = -7409417039339681887L;

    private static final String DEFAULT_MESSAGE = "User: %s not found";

    public UserNotFoundException(String userEmail) {
        super(String.format(DEFAULT_MESSAGE, userEmail), HttpStatus.NOT_FOUND);
    }
}
