package com.notesapp.backend.config.oauth;

import com.notesapp.backend.config.oauth.interfaces.OAuthUserBuilder;
import com.notesapp.backend.entities.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class OAuthUserBuilderImpl implements OAuthUserBuilder {

    public final <T extends OAuth2User> User buildUser(T user,
                                                       Function<T, String> emailExtractor,
                                                       Function<T, String> nameExtractor,
                                                       Function<T, String> lastNameExtractor) {
        String email = emailExtractor.apply(user);
        String name = nameExtractor.apply(user);
        String lastName = lastNameExtractor.apply(user);

        log.info("Returning User with email: {}", email);

        return buildOAuthUser(email, name, lastName);
    }

    private User buildOAuthUser(String email, String name, String lastName) {
        return User.builder()
                .email(email)
                .firstName(name)
                .lastName(lastName)
                .isRegisteredWithOAuth(true)
                .build();
    }
}
