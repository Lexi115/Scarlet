package io.lexi115.projectscarlet.auth.jwt;

import io.lexi115.projectscarlet.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing an issued refresh token.
 *
 * @author Lexi115
 * @since 1.0
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {

    /**
     * The token ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    /**
     * The user that holds the token.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * The user ID.
     */
    @Column(name = "user_id")
    private UUID userId;

    /**
     * The actual token string.
     */
    @Column(name = "token_hash", nullable = false)
    private String tokenHash;

    /**
     * The token expiration date.
     */
    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    /**
     * Whether the token has been revoked (already used to refresh the tokens or logout).
     */
    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked = false;

}
