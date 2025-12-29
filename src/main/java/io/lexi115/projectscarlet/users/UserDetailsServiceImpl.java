package io.lexi115.projectscarlet.users;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service provides custom user authentication details for integration with Spring Security.
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
     * @param username the username of the user to be loaded.
     * @return the user details associated with the given username.
     * @throws UsernameNotFoundException if no user is found with the specified username.
     */
    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull final String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(user);
    }
}
