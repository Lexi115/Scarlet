package io.lexi115.projectscarlet.users;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthenticatedUserSummary {
    private String username;
    private Collection<UserRoleSummary> roles;
}
