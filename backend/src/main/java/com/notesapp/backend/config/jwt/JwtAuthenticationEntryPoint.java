package com.notesapp.backend.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notesapp.backend.utils.exceptions.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        if(response.getContentType() == null) {
            response.setContentType("application/json");

            ResponseEntity<ExceptionResponse> responseEntity = buildDefaultResponseEntity();
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));

        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private ResponseEntity<ExceptionResponse> buildDefaultResponseEntity() {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionName("AuthenticationException")
                .message("Bad credentials")
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .body(exceptionResponse);
    }
}
