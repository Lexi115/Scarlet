package io.lexi115.projectscarlet.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * Entity class for user operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@Entity
@Table(name = "operations")
public class UserOperation implements GrantedAuthority {
    /**
     * The operation ID.
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * Returns the ID of this operation.
     *
     * @return The operation ID.
     * @since 1.0
     */
    @Override
    public String getAuthority() {
        return id;
    }
}
