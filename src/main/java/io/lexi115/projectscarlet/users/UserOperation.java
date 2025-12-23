package io.lexi115.projectscarlet.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "operations")
public class UserOperation implements GrantedAuthority {

    @Id
    @Column(name = "id")
    private String id;

    @Override
    public String getAuthority() {
        return id;
    }
}
