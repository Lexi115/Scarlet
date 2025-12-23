package io.lexi115.projectscarlet.users;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private UserRepository userRepository;

    public UserSummary getUserById(@NonNull final UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        return userMapper.toSummary(user);
    }

    public UserSummary getUserByUsername(@NonNull final String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toSummary(user);
    }

    @Transactional
    public UserSummary createUser(@NonNull final CreateUserRequest request) {
        try {
            var user = userMapper.toUser(request);
            user.addRole(new UserRole(UserRole.DEFAULT));
            userRepository.saveAndFlush(user);
            return userMapper.toSummary(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(request.getUsername());
        }
    }
}
