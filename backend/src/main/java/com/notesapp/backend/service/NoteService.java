package com.notesapp.backend.service;

import com.notesapp.backend.entity.Note;

import java.util.List;

public interface NoteService {

    Note findById(int id);

    List<Note> findAll();

    Note save(Note note);

    Note deleteById(int id);

    Note update(Integer id, Note note);
}
