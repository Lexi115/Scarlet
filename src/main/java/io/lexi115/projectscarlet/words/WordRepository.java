package io.lexi115.projectscarlet.words;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for word-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
public interface WordRepository extends JpaRepository<@NonNull Word, @NonNull Integer> { }
