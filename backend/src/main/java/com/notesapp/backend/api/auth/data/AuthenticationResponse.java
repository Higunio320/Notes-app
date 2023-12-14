package com.notesapp.backend.api.auth.data;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
