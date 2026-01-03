package io.lexi115.projectscarlet.users;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} that loads users from the app's MySQL database.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    /**
     * Loads the user details associated with the given username.
     * This method is used internally by Spring Security to authenticate the user.
     *
     * @param username The username of the user to be loaded.
     * @return The user details associated with the given username.
     * @throws UsernameNotFoundException If no user is found with the specified username.
     * @since 1.0
     */
    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull final String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(user);
    }

}
