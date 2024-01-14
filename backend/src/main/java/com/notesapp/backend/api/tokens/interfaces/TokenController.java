package com.notesapp.backend.api.tokens.interfaces;

import com.notesapp.backend.entities.user.User;
import org.springframework.http.ResponseEntity;

public interface TokenController {

    ResponseEntity<Void> checkToken(User user);
}
