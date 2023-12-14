package com.notesapp.backend.entities.note.interfaces;

import com.notesapp.backend.entities.note.Note;
import com.notesapp.backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);
}
