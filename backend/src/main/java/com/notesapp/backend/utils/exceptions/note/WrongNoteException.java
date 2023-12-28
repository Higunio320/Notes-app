package com.notesapp.backend.utils.exceptions.note;

import com.notesapp.backend.utils.exceptions.BackendException;

public class WrongNoteException extends BackendException {

    public WrongNoteException(String message) {
        super(message);
    }
}
