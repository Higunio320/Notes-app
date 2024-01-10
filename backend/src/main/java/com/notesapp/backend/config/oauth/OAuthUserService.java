package com.notesapp.backend.config.oauth;

import com.notesapp.backend.config.oauth.data.OAuthUserImpl;
import com.notesapp.backend.config.oauth.interfaces.OAuthUser;
import com.notesapp.backend.config.oauth.interfaces.OAuthUserBuilder;
import com.notesapp.backend.entities.user.User;
import com.notesapp.backend.entities.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.notesapp.backend.config.oauth.constants.OAuthConstants.PROVIDER_GITHUB;
import static com.notesapp.backend.config.oauth.constants.OAuthConstants.PROVIDER_GOOGLE;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserService {

    private final UserRepository userRepository;
    private final OAuthUserBuilder oAuthUserBuilder;

    private static final List<String> providers = List.of(PROVIDER_GITHUB, PROVIDER_GOOGLE);
    private static final String PROVIDER_NOT_SUPPORTED = "Provider %s is not supported";

    public final <T extends OAuth2User, U extends OAuth2UserRequest> OAuthUser handleOAuthUser(T user, U request) {
        log.info("Handling OAuth2User: {}", user.getName());

        String provider = request.getClientRegistration().getRegistrationId();

        if(!providers.contains(provider)) {
            throw new IllegalStateException(String.format(PROVIDER_NOT_SUPPORTED, provider));
        }

        User oAuthUser = switch(provider) {
            case PROVIDER_GITHUB -> oAuthUserBuilder.buildUser(user,
                    u -> String.format("%s@githubmail.com", (String) Objects.requireNonNull(u.getAttribute("name"))),
                    u -> u.getAttribute("name"),
                    u -> u.getAttribute("name"));
            case PROVIDER_GOOGLE -> oAuthUserBuilder.buildUser(user,
                    u -> u.getAttribute("email"),
                    u -> u.getAttribute("name"),
                    u -> u.getAttribute("name"));
            default -> throw new IllegalStateException(String.format(PROVIDER_NOT_SUPPORTED, provider));

        };

        Optional<User> userOptional = userRepository.findByEmail(oAuthUser.getEmail());

        if(userOptional.isEmpty()) {
            log.info("Saving new OAuth2User: {}", oAuthUser.getEmail());

            userRepository.save(oAuthUser);
        }

        return switch(provider) {
            case PROVIDER_GITHUB -> buildOAuthUser(user, oAuthUser);
            case PROVIDER_GOOGLE -> buildOidcUser((OidcUser) user, oAuthUser);
            default -> throw new IllegalStateException(String.format(PROVIDER_NOT_SUPPORTED, provider));
        };
    }

    private OAuthUser buildOidcUser(OidcUser user, User oAuthUser) {
        return OAuthUserImpl.builder()
                .userName(oAuthUser.getEmail())
                .name(oAuthUser.getFirstName())
                .userInfo(user.getUserInfo())
                .attributes(user.getAttributes())
                .claims(user.getClaims())
                .authorities(user.getAuthorities())
                .idToken(user.getIdToken())
                .build();
    }

    private OAuthUser buildOAuthUser(OAuth2AuthenticatedPrincipal user, User oAuthUser) {
        return OAuthUserImpl.builder()
                .userName(oAuthUser.getEmail())
                .name(oAuthUser.getFirstName())
                .attributes(user.getAttributes())
                .authorities(user.getAuthorities())
                .build();
    }
}
