package com.notesapp.backend.api.auth;

import com.notesapp.backend.api.auth.data.AuthenticationRequest;
import com.notesapp.backend.api.auth.data.AuthenticationResponse;
import com.notesapp.backend.api.auth.data.RegisterRequest;
import com.notesapp.backend.api.auth.interfaces.AuthService;
import com.notesapp.backend.config.jwt.interfaces.JwtService;
import com.notesapp.backend.entities.user.User;
import com.notesapp.backend.entities.user.interfaces.UserRepository;
import com.notesapp.backend.utils.exceptions.auth.UserAlreadyExistsException;
import com.notesapp.backend.utils.exceptions.auth.UserRegisteredWithOAuthException;
import com.notesapp.backend.utils.exceptions.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtServiceImpl;

    private final AuthenticationManager authenticationManager;

    private static final String JWT_RETURN = "Returning JWT token";

    @Override
    public final AuthenticationResponse register(RegisterRequest request) {
        log.info("Checking if user: {} does not exist", request.email());
        Optional<User> findUser = userRepository.findByEmail(request.email());

        if(findUser.isPresent()) {
            throw new UserAlreadyExistsException(request.email());
        }

        log.info("Creating user for username: {}", request.email());

        User user = User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser = userRepository.save(user);

        log.info("Saved user: {}", savedUser);

        String jwtToken = jwtServiceImpl.generateToken(savedUser);

        log.info(JWT_RETURN);
        return  AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public final AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticating request for user: {}", request.email());

        log.info("Fetching user: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        if(user.isRegisteredWithOAuth()) {
            throw new UserRegisteredWithOAuthException(user.getEmail());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String jwtToken = jwtServiceImpl.generateToken(user);

        log.info(JWT_RETURN);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
