package com.notesapp.backend.utils.exceptions.note;


import com.notesapp.backend.utils.exceptions.BackendException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NoteNotFoundException extends BackendException {

    @Serial
    private static final long serialVersionUID = -3065286068398371228L;

    private static final String EXCEPTION_MESSAGE = "Note of id: %d does not exist";

    public NoteNotFoundException(long id) {
        super(String.format(EXCEPTION_MESSAGE, id), HttpStatus.NOT_FOUND);
    }
}
