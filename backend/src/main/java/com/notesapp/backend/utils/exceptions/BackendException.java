package com.notesapp.backend.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BackendException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -406039100472059084L;

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public BackendException(String message) {
        super(message);
    }

    public BackendException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
