package com.notesapp.backend.exception;

public class WrongNoteException extends NoteException{

    public WrongNoteException(String message) {
        super(message);
    }
}
