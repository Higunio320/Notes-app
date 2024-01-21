package com.notesapp.backend.entities.user.constants;

public final class UserConstants {

    public static final String USER_FIRST_NAME_BLANK = "First name cannot be null nor contain only whitespace characters";
    public static final String USER_FIRST_NAME_SIZE_MSG = "First name must contain max 20 characters";
    public static final int USER_FIRST_NAME_MAX_SIZE = 20;

    public static final String USER_LAST_NAME_BLANK = "Last name cannot be null nor contain only whitespace characters";
    public static final String USER_LAST_NAME_SIZE_MSG = "Last name must contain max 30 characters";
    public static final int USER_LAST_NAME_MAX_SIZE = 30;

    public static final String USER_EMAIL_WRONG = "Email must be a valid email";
    public static final String USER_EMAIL_NULL = "Email cannot be null";
    public static final String USER_EMAIL_SIZE_MSG = "Email must contain max 50 characters";
    public static final String USER_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final int USER_EMAIL_MAX_SIZE = 50;

    public static final String USER_PASSWORD_NULL = "Password cannot be null";

    private UserConstants() {}
}
