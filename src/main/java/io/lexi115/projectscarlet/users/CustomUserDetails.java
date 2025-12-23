package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * A custom implementation of Spring Security's {@link UserDetails} interface.
 * It includes extra fields that are used to generate JWTs.
 *
 * @param user The user entity.
 */
public record CustomUserDetails(User user) implements UserDetails {

    /**
     * Retrieves the unique identifier (UUID) of the user.
     *
     * @return the UUID of the user.
     */
    public UUID getId() {
        return user.getId();
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user as a String.
     */
    @Override
    public @NonNull String getUsername() {
        return user.getUsername();
    }

    /**
     * Retrieves the password of the associated user.
     *
     * @return the user's password as a {@code String}.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return a collection of granted authorities associated with the user.
     */
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new HashSet<GrantedAuthority>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
            role.getOperations().forEach(operation
                    -> authorities.add(new SimpleGrantedAuthority("OP_" + operation.getAuthority())));
        });
        return authorities;
    }
}
