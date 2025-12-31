package io.lexi115.projectscarlet.words;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for word-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@Repository
public interface WordRepository extends JpaRepository<@NonNull Word, @NonNull Integer> { }
