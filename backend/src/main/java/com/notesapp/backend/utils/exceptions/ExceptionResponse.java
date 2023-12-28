package com.notesapp.backend.utils.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(String exceptionName, String message, LocalDateTime timeStamp) {
}
