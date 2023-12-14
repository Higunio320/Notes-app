package com.notesapp.backend.api.notes;


import com.notesapp.backend.api.notes.data.NoteRequest;
import com.notesapp.backend.api.notes.data.NoteResponse;
import com.notesapp.backend.api.notes.interfaces.NoteController;
import com.notesapp.backend.entities.note.Note;
import com.notesapp.backend.api.notes.interfaces.NoteService;
import com.notesapp.backend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.API_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_CREATE_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_DELETE_BY_ID_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_FIND_ALL_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_FIND_BY_ID_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_UPDATE_MAPPING;

@RestController
@RequestMapping(value = API_MAPPING)
@RequiredArgsConstructor
public class NoteControllerImpl implements NoteController {

    private final NoteService noteService;

    @Override
    @PostMapping(NOTE_CREATE_MAPPING)
    public final NoteResponse save(@RequestBody NoteRequest noteRequest,
                                   @AuthenticationPrincipal User user) {
        return noteService.save(noteRequest, user);
    }

    @Override
    @GetMapping(NOTE_FIND_ALL_MAPPING)
    public final List<NoteResponse> findAll(@AuthenticationPrincipal User user) {
        return noteService.findAll(user);
    }

    @Override
    @GetMapping(NOTE_FIND_BY_ID_MAPPING)
    public final NoteResponse findById(@RequestParam long id,
                                       @AuthenticationPrincipal User user) {
        return noteService.findById(id, user);
    }

    @Override
    @PutMapping(NOTE_UPDATE_MAPPING)
    public final NoteResponse update(@RequestParam long id,
                      @RequestBody NoteRequest noteRequest,
                      @AuthenticationPrincipal User user) {
        return noteService.update(id, noteRequest, user);
    }

    @DeleteMapping(NOTE_DELETE_BY_ID_MAPPING)
    public final NoteResponse deleteById(@RequestParam long id,
                                         @AuthenticationPrincipal User user) {
        return noteService.deleteById(id, user);
    }
}
