package io.lexi115.projectscarlet.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class representing a user.
 *
 * @author Lexi115
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    /**
     * The user ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The username.
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * The user's password.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The user's roles.
     */
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    /**
     * The amount of game wins this user has.
     */
    @Column(name = "wins", nullable = false)
    private Integer wins = 0;

    /**
     * The ID of the last won game by this user.
     */
    @Column(name = "last_guess_id")
    private UUID lastGuessId;

    /**
     * Adds a role to this user. This has no effect if user already has that role.
     *
     * @param role The role to add.
     * @since 1.0
     */
    public void addRole(final UserRole role) {
        roles.add(role);
    }

    /**
     * Removes a role from this user. This has no effect if user already doesn't have that role.
     *
     * @param role The role to remove.
     * @since 1.0
     */
    public void removeRole(final UserRole role) {
        roles.remove(role);
    }

}
