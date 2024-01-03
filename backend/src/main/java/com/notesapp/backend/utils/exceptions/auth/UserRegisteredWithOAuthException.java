package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

public class UserRegisteredWithOAuthException extends BackendException {

    private static final String DEFAULT_MESSAGE = "User %s is registered with OAuth therefore cannot be authenticated with password";

    public UserRegisteredWithOAuthException(String mail) {
        super(String.format(DEFAULT_MESSAGE, mail), HttpStatus.FORBIDDEN);
    }
}
