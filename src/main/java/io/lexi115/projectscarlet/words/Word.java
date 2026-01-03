package io.lexi115.projectscarlet.words;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Entity class representing a word.
 *
 * @author Lexi115
 * @since 1.0
 */
@Entity
@Table(name = "words")
@Getter
public class Word {

    /**
     * The word ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The word itself.
     */
    @Column(name = "value", nullable = false)
    private String value;

}
