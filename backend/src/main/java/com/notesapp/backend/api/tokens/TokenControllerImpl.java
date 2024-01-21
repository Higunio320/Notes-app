package com.notesapp.backend.api.tokens;

import com.notesapp.backend.api.tokens.interfaces.TokenController;
import com.notesapp.backend.entities.user.User;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.notesapp.backend.api.tokens.constants.TokenControllerConstants.CHECK_TOKEN_MAPPING;
import static com.notesapp.backend.api.tokens.constants.TokenControllerConstants.TOKEN_API_MAPPING;

@RestController
@NoArgsConstructor
@RequestMapping(TOKEN_API_MAPPING)
public class TokenControllerImpl implements TokenController {
    @Override
    @GetMapping(CHECK_TOKEN_MAPPING)
    public ResponseEntity<Void> checkToken(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().build();
    }
}
