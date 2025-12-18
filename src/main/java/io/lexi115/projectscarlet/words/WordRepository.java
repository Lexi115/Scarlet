package io.lexi115.projectscarlet.words;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<@NonNull Word, @NonNull Integer> { }
