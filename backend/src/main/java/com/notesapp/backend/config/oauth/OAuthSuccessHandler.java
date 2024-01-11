package com.notesapp.backend.config.oauth;

import com.notesapp.backend.config.jwt.interfaces.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.notesapp.backend.config.constants.SecurityConfigConstants.ENV_FRONTEND_URL;
import static com.notesapp.backend.config.oauth.constants.OAuthConstants.FRONTEND_OAUTH_MAPPING;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public final void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if(response.isCommitted()) {
            return;
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String email = user.getUsername();

        log.info("Authentication success for user: {}", email);

        String jwtToken = jwtService.generateToken(user);

        String frontendUrl = System.getenv(ENV_FRONTEND_URL);

        String redirectionUrl = UriComponentsBuilder.fromUriString(String.format("%s%s",
                        frontendUrl,
                        FRONTEND_OAUTH_MAPPING))
                .queryParam("token", jwtToken)
                .build().toUriString();
        response.sendRedirect(redirectionUrl);
    }
}
