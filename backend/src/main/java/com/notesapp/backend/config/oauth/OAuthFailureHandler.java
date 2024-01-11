package com.notesapp.backend.config.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notesapp.backend.utils.exceptions.BackendExceptionHandler;
import com.notesapp.backend.utils.exceptions.ExceptionResponse;
import com.notesapp.backend.utils.exceptions.auth.OAuthErrorException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthFailureHandler implements AuthenticationFailureHandler {

    private final BackendExceptionHandler backendExceptionHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseEntity<ExceptionResponse> responseEntity = backendExceptionHandler.handleBackendException(new OAuthErrorException());

        String responseBody = objectMapper.writeValueAsString(responseEntity.getBody());

        response.getWriter().write(responseBody);
        response.setContentType("application/json");
        response.setStatus(responseEntity.getStatusCode().value());
    }
}
