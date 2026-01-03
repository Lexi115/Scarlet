package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that authorizes a user with the provided JSON Web Token (JWT) in the request header.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    /**
     * The JWT service.
     */
    private final JwtService jwtService;

    /**
     * The user details service (used to retrieve the user from the database).
     */
    private final UserDetailsService userDetailsService;

    /**
     * The filter logic.
     *
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @param filterChain The filter chain.
     * @throws ServletException If a servlet error occurs during execution.
     * @throws IOException      If a I/O error occurs during execution.
     * @since 1.0
     */
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // Check security context
        var context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get JWT string from Authorization request header
        var token = getTokenFromRequest(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Parse JWT from string
        var jwt = parseJwtFromToken(token);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get corresponding user details
        var userDetails = getUserDetailsFromJwt(jwt);
        if (userDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Validate JWT
        if (!jwtService.validateJwt(jwt, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Set user in security context
        var authentication = getAuthenticationFromUserDetails(userDetails, request);
        context.setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private @Nullable String getTokenFromRequest(@NonNull final HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader;
    }

    private @Nullable Jwt parseJwtFromToken(@NonNull final String token) {
        try {
            // Remove the "Bearer " prefix
            return jwtService.decodeJwt(token.trim().substring(7));
        } catch (JwtException e) {
            return null;
        }
    }

    private @Nullable UserDetails getUserDetailsFromJwt(@NonNull final Jwt jwt) {
        var jwtSubject = jwt.getSubject();
        if (jwtSubject == null) {
            return null;
        }
        try {
            return userDetailsService.loadUserByUsername(jwtSubject);
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

    private @NonNull Authentication getAuthenticationFromUserDetails(
            @NonNull final UserDetails userDetails,
            @NonNull final HttpServletRequest request
    ) {
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
