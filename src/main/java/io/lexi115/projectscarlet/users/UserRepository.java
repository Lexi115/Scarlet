package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<@NonNull User, @NonNull UUID> {
    @EntityGraph(attributePaths = {"roles", "roles.operations"})
    Optional<@NonNull User> findByUsername(@NonNull String username);
}
