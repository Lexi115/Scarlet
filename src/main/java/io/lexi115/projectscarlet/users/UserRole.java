package io.lexi115.projectscarlet.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    public static final String DEFAULT = "DEFAULT";
    public static final String ADMIN = "ADMIN";

    @Id
    @Column(name = "id")
    private String id;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "roles_operations",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id")
    )
    private final Set<UserOperation> operations = new HashSet<>();

    @Override
    public String getAuthority() {
        return id;
    }

    public UserRole(final String id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
