package com.notesapp.backend.api.notes;


import com.notesapp.backend.api.notes.data.NoteListResponse;
import com.notesapp.backend.api.notes.data.NoteRequest;
import com.notesapp.backend.api.notes.data.NoteResponse;
import com.notesapp.backend.api.notes.interfaces.NoteController;
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

import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTES_API_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_CREATE_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_DELETE_BY_ID_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_FIND_ALL_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_FIND_BY_ID_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_GET_BY_TEXT_MAPPING;
import static com.notesapp.backend.api.notes.constants.NoteControllerConstants.NOTE_UPDATE_MAPPING;

@RestController
@RequestMapping(value = NOTES_API_MAPPING)
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
    public final NoteListResponse findAll(@AuthenticationPrincipal User user,
                                                @RequestParam int pageNumber) {
        return noteService.findAll(pageNumber, user);
    }

    @Override
    @GetMapping(NOTE_GET_BY_TEXT_MAPPING)
    public NoteListResponse findByText(@AuthenticationPrincipal User user,
                                         @RequestParam String text,
                                         @RequestParam int pageNumber) {
        return noteService.findByText(user, text, pageNumber);
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
