package io.lexi115.projectscarlet.users;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * A custom implementation of Spring Security's {@link UserDetails} interface.
 *
 * @author Lexi115
 * @since 1.0
 */
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    /**
     * The user entity.
     */
    private User user;

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     * @since 1.0
     */
    @Override
    public @NonNull String getUsername() {
        return user.getUsername();
    }

    /**
     * Retrieves the (most likely hashed) password of the associated user.
     *
     * @return The user's password.
     * @since 1.0
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of granted authorities associated with the user.
     * @since 1.0
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
