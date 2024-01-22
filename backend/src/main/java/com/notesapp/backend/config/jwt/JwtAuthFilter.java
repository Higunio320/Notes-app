package com.notesapp.backend.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notesapp.backend.config.jwt.interfaces.JwtService;
import com.notesapp.backend.entities.invalidtoken.interfaces.InvalidTokenRepository;
import com.notesapp.backend.utils.exceptions.BackendException;
import com.notesapp.backend.utils.exceptions.BackendExceptionHandler;
import com.notesapp.backend.utils.exceptions.ExceptionResponse;
import com.notesapp.backend.utils.exceptions.auth.InvalidJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtServiceImpl;
    private final UserDetailsService userDetailsService;
    private final BackendExceptionHandler backendExceptionHandler;
    private final InvalidTokenRepository invalidTokenRepository;
    private final ObjectMapper objectMapper;

    private static final List<String> AUTH_WHITELIST = List.of("/api/auth/", "/oauth/");

    @Override
    protected final void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ") || isAuthRequest(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        if(invalidTokenRepository.existsByToken(jwt)) {
            writeExceptionToResponse(response, new InvalidJWTException("Token is expired"));

            filterChain.doFilter(request, response);
            return;
        }
        try {
            userEmail = jwtServiceImpl.extractUsername(jwt);
        } catch (InvalidJWTException e) {
            writeExceptionToResponse(response, e);
            return;
        }
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if(jwtServiceImpl.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("User authenticated: {}", userEmail);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAuthRequest(String requestUri) {
        return AUTH_WHITELIST.stream().anyMatch(requestUri::startsWith);
    }

    private void writeExceptionToResponse(HttpServletResponse response, BackendException exception) throws  IOException {
        ResponseEntity<ExceptionResponse> responseEntity = backendExceptionHandler.handleBackendException(exception);

        String jsonBody = objectMapper.writeValueAsString(Objects.requireNonNull(responseEntity.getBody()));
        response.getWriter().write(jsonBody);
        response.setContentType("/application/json");
    }
}
