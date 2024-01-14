package com.notesapp.backend.api.notes;

import com.notesapp.backend.api.notes.data.NoteListResponse;
import com.notesapp.backend.api.notes.data.NoteRequest;
import com.notesapp.backend.api.notes.data.NoteResponse;
import com.notesapp.backend.api.notes.interfaces.NoteService;
import com.notesapp.backend.entities.note.Note;
import com.notesapp.backend.entities.note.interfaces.NoteMapper;
import com.notesapp.backend.entities.note.interfaces.NoteRepository;
import com.notesapp.backend.entities.user.User;
import com.notesapp.backend.utils.exceptions.auth.NoAccessException;
import com.notesapp.backend.utils.exceptions.note.NoteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    private static final String GETTING_NOTE_OF_ID = "Getting note of id: {}";
    private static final String CHECKING_ACCESS_TO_NOTE = "Checking if user: {} has access to note of id: {}";
    private static final String RETURNING_NOTE_RESPONSE_OF = "Returning note response of note: {}";
    private static final String GETTING_NOTES_FOR_USER = "Getting notes for user: {}";
    private static final String RETURNING_LIST_OF_NOTES = "Returning note list response of size: {}, total notes: {}";
    private static final String CREATING_NOTE_FOR_REQUEST_AND_USER = "Creating note for request: {} and user: {}";
    private static final String SAVING_NOTE = "Saving note: {}";
    private static final String DELETING_NOTE = "Deleting note: {}";
    private static final int PAGE_SIZE = 8;

    @Override
    public final NoteResponse findById(long id, User user) {
        log.info(GETTING_NOTE_OF_ID, id);

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if(!userHasAccessToNote(user, note)) {
            throw new NoAccessException(id, user.getEmail());
        }

        log.info(RETURNING_NOTE_RESPONSE_OF, note);

        return noteMapper.toNoteResponse(note);
    }

    @Override
    public final NoteListResponse findAll(int pageNumber, User user) {
        log.info(GETTING_NOTES_FOR_USER, user.getEmail());

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Note> notes = noteRepository.findByUserOrderByLastEditDesc(user, pageable);

        long totalNotes = notes.getTotalElements();

        log.info(RETURNING_LIST_OF_NOTES, notes.getNumberOfElements(), totalNotes);

        List<NoteResponse> notesList = notes.stream()
                .map(noteMapper::toNoteResponse)
                .toList();


        return NoteListResponse.builder()
                .notes(notesList)
                .totalNotes(totalNotes)
                .build();
    }

    @Override
    public final NoteListResponse findByText(User user, String text, int pageNumber) {
        log.info("Getting notes for user: {} \n containing: {}", user.getEmail(), text);

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Note> notes = noteRepository.findByUserAndNoteContaining(user, text, pageable);

        long totalNotes = notes.getTotalElements();

        log.info(RETURNING_LIST_OF_NOTES, notes.getNumberOfElements(), totalNotes);

        List<NoteResponse> notesList = notes.stream()
                .map(noteMapper::toNoteResponse)
                .toList();

        return NoteListResponse.builder()
                .notes(notesList)
                .totalNotes(totalNotes)
                .build();
    }

    @Override
    public final NoteResponse save(NoteRequest note, User user) {
        log.info(CREATING_NOTE_FOR_REQUEST_AND_USER, note, user.getEmail());

        Note noteToSave = Note.builder()
                .title(note.title())
                .text(note.text())
                .user(user)
                .lastEdit(LocalDateTime.now())
                .build();

        log.info(SAVING_NOTE, noteToSave);

        Note savedNote = noteRepository.save(noteToSave);

        log.info(RETURNING_NOTE_RESPONSE_OF, savedNote);

        return noteMapper.toNoteResponse(savedNote);
    }

    @Override
    public final NoteResponse deleteById(long id, User user) {
        log.info(GETTING_NOTE_OF_ID, id);

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if(!userHasAccessToNote(user, note)) {
            throw new NoAccessException(id, user.getEmail());
        }

        log.info(DELETING_NOTE, note);

        noteRepository.delete(note);

        log.info(RETURNING_NOTE_RESPONSE_OF, note);

        return noteMapper.toNoteResponse(note);
    }

    @Override
    public final NoteResponse update(long id, NoteRequest note, User user) {
        log.info(GETTING_NOTE_OF_ID, id);

        Note noteToUpdate = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if(!userHasAccessToNote(user, noteToUpdate)) {
            throw new NoAccessException(id, user.getEmail());
        }

        noteToUpdate.setTitle(note.title());
        noteToUpdate.setText(note.text());
        noteToUpdate.setLastEdit(LocalDateTime.now());

        log.info(SAVING_NOTE, noteToUpdate);

        Note savedNote = noteRepository.save(noteToUpdate);

        log.info(RETURNING_NOTE_RESPONSE_OF, savedNote);

        return noteMapper.toNoteResponse(savedNote);
    }

    private boolean userHasAccessToNote(User user, Note note) {
        log.info(CHECKING_ACCESS_TO_NOTE, user.getEmail(), note.getId());

        User noteUser = note.getUser();

        return user.equals(noteUser);
    }
}
