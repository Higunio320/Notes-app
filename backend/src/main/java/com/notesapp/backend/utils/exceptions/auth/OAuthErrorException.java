package com.notesapp.backend.utils.exceptions.auth;

import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class OAuthErrorException extends BackendException {

    @Serial
    private static final long serialVersionUID = -7075096561831201147L;

    private static final String DEFAULT_MESSAGE = "User could not be authenticated with OAuth";

    public OAuthErrorException() {
        super(DEFAULT_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
