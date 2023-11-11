package com.notesapp.backend.controller;


import com.notesapp.backend.entity.Note;
import com.notesapp.backend.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/notes")
@AllArgsConstructor
public class NoteController {

    private NoteService noteService;

    @PostMapping
    Note create(@RequestBody Note note) {
        return noteService.save(note);
    }

    @GetMapping
    List<Note> findAll() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    Note findById(@PathVariable Integer id) {
        return noteService.findById(id);
    }

    @PutMapping("/{id}")
    Note save(@PathVariable Integer id, @RequestBody Note note) {
        return noteService.update(id, note);
    }

    @DeleteMapping("{id}")
    Note deleteById(@PathVariable Integer id) {
        return noteService.deleteById(id);
    }
}
