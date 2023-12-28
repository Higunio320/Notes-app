package com.notesapp.backend.utils.exceptions.note;

import com.notesapp.backend.utils.exceptions.BackendException;

public class NullArgumentException extends BackendException {

    public NullArgumentException(String message) {
        super(message);
    }
}
