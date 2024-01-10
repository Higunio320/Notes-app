package com.notesapp.backend.utils.exceptions.config;

import java.io.Serial;

public class EnvironmentVariableNotSetException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5077448392232334763L;

    private static final String DEFAULT_MESSAGE = "Environment variable %s not set";

    public EnvironmentVariableNotSetException(String variableName) {
            super(String.format(DEFAULT_MESSAGE, variableName));
        }
}
