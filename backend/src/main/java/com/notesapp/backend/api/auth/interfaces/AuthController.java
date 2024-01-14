package com.notesapp.backend.api.auth.interfaces;


import com.notesapp.backend.api.auth.data.AuthenticationRequest;
import com.notesapp.backend.api.auth.data.AuthenticationResponse;
import com.notesapp.backend.api.auth.data.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
