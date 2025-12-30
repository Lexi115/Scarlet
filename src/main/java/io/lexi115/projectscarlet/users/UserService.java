package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.cache.CacheService;
import io.lexi115.projectscarlet.words.GuessResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final CacheService cacheService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserSummary getUserByUsername(@NonNull final String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toSummary(user);
    }

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

    @Transactional
    public void incrementUserWins(@NonNull final GuessResponse guessResponse, @NonNull final String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var guessId = cacheService.get("guessId");
        var userLastGuessId = user.getLastGuessId();
        // Do not increment wins if user already solved current word or guessed wrong
        if ((userLastGuessId != null && userLastGuessId.toString().equals(guessId)) || !guessResponse.isCorrect()) {
            return;
        }
        user.setWins(user.getWins() + 1);
        user.setLastGuessId(UUID.fromString(guessId));
        userRepository.save(user);
    }
}
