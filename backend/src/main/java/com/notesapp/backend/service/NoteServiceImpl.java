package com.notesapp.backend.service;

import com.notesapp.backend.entity.Note;
import com.notesapp.backend.exception.NoteNotFoundException;
import com.notesapp.backend.exception.NullArgumentException;
import com.notesapp.backend.exception.WrongNoteException;
import com.notesapp.backend.repository.NoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class NoteServiceImpl implements NoteService{
    private static final String GET_NOTE_OF_ID = "Getting note of id: {}";
    private static final String NOTE_DOESNT_EXIST = "Note of id %d doesn't exist";

    private NoteRepository noteRepository;

    @Override
    public Note findById(int id) {
        log.info(GET_NOTE_OF_ID, id);

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_DOESNT_EXIST, id)));

        log.info("Returning note of id: {}", id);

        return note;
    }

    @Override
    public List<Note> findAll() {
        log.info("Returning list of all notes");

        return noteRepository.findAll();
    }

    @Override
    public Note save(Note note) {
        if(note == null) {
            throw new NullArgumentException("Given note is null");
        }
        if(note.getId() != null) {
            throw new WrongNoteException("Provide note without an id");
        }

        Note savedNote = noteRepository.save(note);

        log.info("Returning saved note of id: {}", savedNote.getId());

        return savedNote;
    }

    @Override
    public Note deleteById(int id) {
        log.info(GET_NOTE_OF_ID, id);

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_DOESNT_EXIST, id)));

        noteRepository.deleteById(id);

        log.info("Returning deleted note of id: {}", id);

        return note;
    }

    @Override
    public Note update(Integer id, Note note) {
        if(id == null) {
            throw new NullArgumentException("Given id is null");
        }
        if(note == null) {
            throw new NullArgumentException("Given note is null");
        }

        log.info(GET_NOTE_OF_ID, id);

        noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_DOESNT_EXIST, id)));

        note.setId(id);

        Note savedNote = noteRepository.save(note);

        log.info("Returning saved note of id: {}", savedNote.getId());

        return savedNote;
    }
}
