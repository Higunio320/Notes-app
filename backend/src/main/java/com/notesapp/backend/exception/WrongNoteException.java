package com.notesapp.backend.exception;

public class WrongNoteException extends RuntimeException{

    public WrongNoteException(String message) {
        super(message);
    }
}
