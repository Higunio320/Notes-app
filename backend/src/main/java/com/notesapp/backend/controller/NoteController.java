package com.notesapp.backend.controller;


import com.notesapp.backend.controller.response.CommonResponses;
import com.notesapp.backend.entity.Note;
import com.notesapp.backend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notes")
@AllArgsConstructor
@Tag(name = "Notes operations", description = "All endpoints for notes")
public class NoteController {

    private NoteService noteService;

    @Operation(
            summary = "Create a new note",
            description = "This endpoint creates a new note")
    @CommonResponses
    @PostMapping
    Note create(@RequestBody Note note) {
        return noteService.save(note);
    }

    @Operation(
            summary = "Retrieve all notes",
            description = "This endpoints returns list of all notes")
    @GetMapping
    List<Note> findAll() {
        return noteService.findAll();
    }

    @Operation(
            summary = "Retrieve note of given id",
            description = "This endpoint returns a given note")
    @CommonResponses
    @GetMapping("/{id}")
    Note findById(@PathVariable Integer id) {
        return noteService.findById(id);
    }

    @Operation(
            summary = "Edit note of given id",
            description = "This endpoint edits a note of given id")
    @CommonResponses
    @PutMapping("/{id}")
    Note save(@PathVariable Integer id, @RequestBody Note note) {
        return noteService.update(id, note);
    }

    @Operation(
            summary = "Delete note of given id",
            description = "This endpoints deletes note of given id")
    @CommonResponses
    @DeleteMapping("{id}")
    Note deleteById(@PathVariable Integer id) {
        return noteService.deleteById(id);
    }
}
