package com.notesapp.backend.entities.note.interfaces;

import com.notesapp.backend.entities.note.Note;
import com.notesapp.backend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByUserOrderByLastEditDesc(User user, Pageable pageable);

    @Query("SELECT n FROM Note n WHERE n.user = :user AND (LOWER(n.text) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(n.title) LIKE LOWER(CONCAT('%', :text, '%'))) ORDER BY n.lastEdit DESC")
    Page<Note> findByUserAndNoteContaining(@Param("user") User user, @Param("text") String text, Pageable pageable);
}
