package com.notesapp.backend.entities.note.constants;

public final class NoteConstants {

    public static final String NOTE_TITLE_BLANK = "Title cannot be null nor contain only whitespace characters";
    public static final String NOTE_TITLE_SIZE_MSG = "Title must contain max 50 characters";
    public static final int NOTE_TITLE_MAX_SIZE = 50;

    public static final String NOTE_TEXT_BLANK = "Text cannot be null nor contain only whitespace characters";
    public static final String NOTE_TEXT_SIZE_MSG = "Text must contain max 1000 characters";
    public static final int NOTE_TEXT_MAX_SIZE = 1000;

    public static final String NOTE_DATE_NULL = "Last edit date cannot be null";
    public static final String NOTE_DATE_PAST = "Last edit date cannot be in the future";

    private NoteConstants() {}
}
