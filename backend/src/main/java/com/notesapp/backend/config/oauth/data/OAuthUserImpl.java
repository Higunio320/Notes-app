package com.notesapp.backend.config.oauth.data;

import com.notesapp.backend.config.oauth.interfaces.OAuthUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class OAuthUserImpl implements OAuthUser {

    @Serial
    private static final long serialVersionUID = -3815099291070377630L;
    private final String userName;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;
    private final transient Map<String, Object> attributes;
    private final transient Map<String, Object> claims;
    private final OidcUserInfo userInfo;
    private final OidcIdToken idToken;

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public final String getPassword() {
        throw new UnsupportedOperationException("OAuthUser does not have a password");
    }

    @Override
    public final String getUsername() {
        return userName;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Override
    public final Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public final Map<String, Object> getClaims() {
        return Collections.unmodifiableMap(claims);
    }

    @Override
    public final OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public final OidcIdToken getIdToken() {
        return idToken;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String toString() {
        return userName;
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) {
        throw new UnsupportedOperationException("Not allowed to deserialize this class");
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) {
        throw new UnsupportedOperationException("Not allowed to serialize this class");
    }
}