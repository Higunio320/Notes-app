package com.notesapp.backend.api.auth.data;

import lombok.Builder;

@Builder
public record RegisterRequest(String firstName, String lastName, String email, String password) {
}
