package io.lexi115.projectscarlet.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "wins", nullable = false)
    private Integer wins = 0;

    @Column(name = "last_guess_id")
    private UUID lastGuessId;

    public void addRole(final UserRole role) {
        roles.add(role);
    }

    public void removeRole(final UserRole role) {
        roles.remove(role);
    }
}
