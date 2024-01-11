package com.notesapp.backend.config.oauth.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


public interface OAuthUser extends UserDetails, OidcUser {
}
