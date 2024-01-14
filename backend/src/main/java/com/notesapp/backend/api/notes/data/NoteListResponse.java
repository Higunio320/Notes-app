package com.notesapp.backend.api.notes.data;

import lombok.Builder;

import java.util.List;

@Builder
public record NoteListResponse(List<NoteResponse> notes, long totalNotes) {
}
