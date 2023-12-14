package com.notesapp.backend.api.notes.interfaces;

import com.notesapp.backend.api.notes.data.NoteRequest;
import com.notesapp.backend.api.notes.data.NoteResponse;
import com.notesapp.backend.entities.user.User;

import java.util.List;

public interface NoteService {

    NoteResponse findById(long id, User user);

    List<NoteResponse> findAll(User user);

    NoteResponse save(NoteRequest note, User user);

    NoteResponse deleteById(long id, User user);

    NoteResponse update(long id, NoteRequest note, User user);
}
