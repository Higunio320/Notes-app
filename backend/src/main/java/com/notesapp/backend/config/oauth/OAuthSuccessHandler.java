package com.notesapp.backend.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final String homeUrl = "http://localhost:8080/index.html";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(response.isCommitted()) {
            return;
        }

        Object principal = authentication.getPrincipal();

        String email;
        if(principal instanceof OidcUser oidcUser) {
            email = oidcUser.getEmail();
        } else if(principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
            if(email == null || email.equals("null")) {
                email = String.format("%s@example.com", oAuth2User.getAttribute("login"));
            }
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getSimpleName());
        }

        log.info("Authentication success for user: {}", email);

        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("email", email)
                .build().toUriString();
        response.sendRedirect(redirectionUrl);
    }
}
