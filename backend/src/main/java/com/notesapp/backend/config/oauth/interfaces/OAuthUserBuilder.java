package com.notesapp.backend.config.oauth.interfaces;

import com.notesapp.backend.entities.user.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Function;

public interface OAuthUserBuilder {

    <T extends OAuth2User> User buildUser(T user,
                                          Function<T, String> emailExtractor,
                                          Function<T, String> nameExtractor,
                                          Function<T, String> lastNameExtractor);
}
