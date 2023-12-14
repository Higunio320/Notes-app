package com.notesapp.backend.entities.note.interfaces;

import com.notesapp.backend.api.notes.data.NoteResponse;
import com.notesapp.backend.entities.note.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponse toNoteResponse(Note note);
}
