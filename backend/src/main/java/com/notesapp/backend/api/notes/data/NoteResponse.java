package com.notesapp.backend.api.notes.data;

import java.time.LocalDateTime;

public record NoteResponse(long id, String title, String text, LocalDateTime lastEdit) {
}
