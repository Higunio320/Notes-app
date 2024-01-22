package com.notesapp.backend.api.auth.interfaces;

import com.notesapp.backend.api.auth.data.AuthenticationRequest;
import com.notesapp.backend.api.auth.data.AuthenticationResponse;
import com.notesapp.backend.api.auth.data.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(String token);
}
