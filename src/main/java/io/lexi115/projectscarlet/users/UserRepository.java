package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository class for user-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull UUID> {
    /**
     * Finds a user by the username. It automatically fetches the user's roles and granted operations.
     *
     * @param username The username.
     * @return An {@link Optional} object containing the related user if found.
     * @since 1.0
     */
    @EntityGraph(attributePaths = {"roles", "roles.operations"})
    Optional<@NonNull User> findByUsername(@NonNull String username);
}
