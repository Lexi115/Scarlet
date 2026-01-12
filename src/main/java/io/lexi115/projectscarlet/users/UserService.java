package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.cache.CacheService;
import io.lexi115.projectscarlet.core.ScarletConfig;
import io.lexi115.projectscarlet.words.GuessResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for user-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class UserService {

    /**
     * The user mapper.
     */
    private final UserMapper userMapper;

    /**
     * The service used for caching values.
     */
    private final CacheService<Object> cacheService;

    /**
     * The user repository.
     */
    private UserRepository userRepository;

    /**
     * The password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * The app configuration.
     */
    private ScarletConfig scarletConfig;

    /**
     * Gets a user by the username.
     *
     * @param username The username. Cannot be <code>null</code>.
     * @return A summary of the retrieved user.
     * @throws UserNotFoundException If the user is not found.
     * @since 1.0
     */
    public UserSummary getUserByUsername(@NonNull final String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toSummary(user);
    }

    /**
     * Creates a new user.
     *
     * @param request The user creation request.
     * @return A summary of the created user.
     * @throws UserAlreadyExistsException If the provided username is already in use.
     * @since 1.0
     */
    @Transactional
    public UserSummary createUser(@NonNull final CreateUserRequest request) {
        try {
            var user = userMapper.toUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.addRole(new UserRole(UserRole.DEFAULT));
            userRepository.saveAndFlush(user);
            return userMapper.toSummary(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(request.getUsername());
        }
    }

    /**
     * Increases the number of game wins for a certain player by 1, as long as he guessed correctly and for the first
     * time since the last word has been chosen.
     *
     * @param guessResponse The outcome of the game.
     * @param username      The player's username.
     * @since 1.0
     */
    @Transactional
    public void incrementUserWins(@NonNull final GuessResponse guessResponse, @NonNull final String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var guessId = (String) cacheService.get("guessId");
        var userLastGuessId = user.getLastGuessId();
        // Do not increment wins if user already solved current word or guessed wrong
        if ((userLastGuessId != null && userLastGuessId.toString().equals(guessId)) || !guessResponse.isCorrect()) {
            return;
        }
        user.setWins(user.getWins() + 1);
        user.setLastGuessId(UUID.fromString(guessId));
        userRepository.save(user);
    }

    /**
     * Returns a page of the users' leaderboard (sorted by most wins).
     *
     * @param page The page number (must be at least 1).
     * @return The leaderboard page.
     * @since 1.0
     */
    public List<LeaderboardEntry> getLeaderboard(final int page) {
        var pageSize = scarletConfig.getLeaderboardPageSize();
        var pageable = PageRequest.of(page - 1, pageSize, Sort.by("wins").descending());
        return userRepository.findAll(pageable).map(userMapper::toLeaderboardEntry).toList();
    }

}
