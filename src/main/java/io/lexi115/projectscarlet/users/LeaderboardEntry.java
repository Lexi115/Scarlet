package io.lexi115.projectscarlet.users;

import lombok.Data;

/**
 * DTO that represents an entry in the users' leaderboard.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class LeaderboardEntry {

    /**
     * The username.
     */
    private String username;

    /**
     * The amount of wins.
     */
    private Integer wins;

}
