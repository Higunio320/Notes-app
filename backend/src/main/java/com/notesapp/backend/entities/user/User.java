package com.notesapp.backend.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static com.notesapp.backend.entities.user.constants.UserConstants.USER_EMAIL_MAX_SIZE;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_EMAIL_NULL;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_EMAIL_REGEX;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_EMAIL_SIZE_MSG;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_EMAIL_WRONG;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_FIRST_NAME_BLANK;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_FIRST_NAME_MAX_SIZE;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_FIRST_NAME_SIZE_MSG;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_LAST_NAME_BLANK;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_LAST_NAME_MAX_SIZE;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_LAST_NAME_SIZE_MSG;
import static com.notesapp.backend.entities.user.constants.UserConstants.USER_PASSWORD_NULL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Builder
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = USER_FIRST_NAME_BLANK)
    @Size(max = USER_FIRST_NAME_MAX_SIZE, message = USER_FIRST_NAME_SIZE_MSG)
    private String firstName;

    @NotBlank(message = USER_LAST_NAME_BLANK)
    @Size(max = USER_LAST_NAME_MAX_SIZE, message = USER_LAST_NAME_SIZE_MSG)
    private String lastName;

    @Email(message = USER_EMAIL_WRONG, regexp=USER_EMAIL_REGEX)
    @NotNull(message = USER_EMAIL_NULL)
    @Size(max = USER_EMAIL_MAX_SIZE, message = USER_EMAIL_SIZE_MSG)
    private String email;

    @ToString.Exclude
    @NotNull(message = USER_PASSWORD_NULL)
    private String password;

    private boolean isRegisteredWithOAuth = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) object;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
