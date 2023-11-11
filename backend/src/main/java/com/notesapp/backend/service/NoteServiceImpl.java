package com.notesapp.backend.service;

import com.notesapp.backend.entity.Note;
import com.notesapp.backend.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService{

    private NoteRepository noteRepository;


    @Override
    public Note findById(int id) {
        Optional<Note> opt =  noteRepository.findById(id);

        if(opt.isPresent()) {
            return opt.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note has not been found");
        }
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note save(Note note) {
        if(note.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide note without an id");
        }

        return noteRepository.save(note);
    }

    @Override
    public void deleteById(int id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note update(Integer id, Note note) {
        if(!Objects.equals(id, note.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id and note.id are not equal");
        }
        return noteRepository.save(note);
    }
}
