package com.notesapp.backend.config.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyOidcUserService extends OidcUserService {

    private final OAuthUserService oAuthUserService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading OidcUser for request: {}", userRequest);

        OidcUser user = super.loadUser(userRequest);

        log.info("Loaded OidcUser: {}", user.getName());

        return oAuthUserService.handleOAuthUser(user, userRequest);
    }
}
