package com.notesapp.backend.controller;


import com.notesapp.backend.entity.Note;
import com.notesapp.backend.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/notes")
@AllArgsConstructor
public class NoteController {

    private NoteService noteService;

    @PostMapping
    Note create(@RequestBody Note note) {
        log.debug("Create note: {}", note);
        return noteService.save(note);
    }

    @GetMapping
    List<Note> findAll() {
        log.debug("Find all notes");
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    Note findById(@PathVariable Integer id) {
        log.debug("Find note with id: {}", id);
        return noteService.findById(id);
    }

    @PutMapping("/{id}")
    Note save(@PathVariable Integer id, @RequestBody Note note) {
        log.debug("Updating note with id {} with {}", id, note);
        return noteService.update(id, note);
    }

    @DeleteMapping("{id}")
    void deleteById(@PathVariable Integer id) {
        noteService.deleteById(id);
    }
}
