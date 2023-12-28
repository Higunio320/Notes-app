package com.notesapp.backend.api.auth;


import com.notesapp.backend.api.auth.data.AuthenticationRequest;
import com.notesapp.backend.api.auth.data.AuthenticationResponse;
import com.notesapp.backend.api.auth.data.RegisterRequest;
import com.notesapp.backend.api.auth.interfaces.AuthController;
import com.notesapp.backend.api.auth.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.notesapp.backend.api.auth.constants.AuthControllerConstants.AUTH_API_MAPPING;
import static com.notesapp.backend.api.auth.constants.AuthControllerConstants.LOGIN_MAPPING;
import static com.notesapp.backend.api.auth.constants.AuthControllerConstants.REGISTER_MAPPING;

@RestController
@RequestMapping(AUTH_API_MAPPING)
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    @Override
    @PostMapping(REGISTER_MAPPING)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    @PostMapping(LOGIN_MAPPING)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
