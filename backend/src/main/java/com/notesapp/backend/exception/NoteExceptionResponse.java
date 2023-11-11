package com.notesapp.backend.exception;

public record NoteExceptionResponse(String exceptionName, String message, long timeStamp) {
}
