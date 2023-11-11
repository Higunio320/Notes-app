package com.notesapp.backend.exception;

public class NullArgumentException extends NoteException{

    public NullArgumentException(String message) {
        super(message);
    }
}
