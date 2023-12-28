package com.notesapp.backend.api.notes.data;

import lombok.Builder;

@Builder
public record NoteRequest(String title, String text) {
}
