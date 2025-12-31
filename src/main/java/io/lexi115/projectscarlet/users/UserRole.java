package io.lexi115.projectscarlet.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class for user roles.
 *
 * @author Lexi115
 * @since 1.0
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    /**
     * The ID for the default role.
     */
    public static final String DEFAULT = "DEFAULT";

    /**
     * The ID for the admin role.
     */
    public static final String ADMIN = "ADMIN";
    /**
     * A set of operations granted to this role.
     */
    @Getter
    @ManyToMany
    @JoinTable(
            name = "roles_operations",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id")
    )
    private final Set<UserOperation> operations = new HashSet<>();
    /**
     * The role ID.
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * Constructor.
     *
     * @param id The role ID.
     * @since 1.0
     */
    public UserRole(final String id) {
        this.id = id;
    }

    /**
     * Returns the ID of this role.
     *
     * @return The role ID.
     * @since 1.0
     */
    @Override
    public String getAuthority() {
        return id;
    }

    /**
     * Checks if this role equals another one. Two roles are considered equal if the ID is the same.
     *
     * @param obj The reference object with which to compare.
     * @return <code>true</code> if objects are equals, <code>false</code> otherwise.
     * @since 1.0
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserRole castedObj)) {
            return false;
        }
        return this.id.equals(castedObj.id);
    }

    /**
     * Returns the hashcode for this role object.
     *
     * @return The role hashcode.
     * @since 1.0
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
