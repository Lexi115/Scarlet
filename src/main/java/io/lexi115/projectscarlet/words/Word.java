package io.lexi115.projectscarlet.words;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "words")
@Getter
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private String value;
}
