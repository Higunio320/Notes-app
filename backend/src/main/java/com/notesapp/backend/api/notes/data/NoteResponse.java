package com.notesapp.backend.api.notes.data;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteResponse(long id, String title, String text, LocalDateTime lastEdit) {
}
