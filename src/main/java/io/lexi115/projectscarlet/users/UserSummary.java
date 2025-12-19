package io.lexi115.projectscarlet.users;

import lombok.Data;

@Data
public class UserSummary {
    private String username;
    private UserRole role;
    private Integer wins;
}
