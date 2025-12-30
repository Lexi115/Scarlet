package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // Check security context (is user already authenticated?)
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
        var userDetails = getUserFromJwt(jwt);
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

    private String getTokenFromRequest(final @NonNull HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader;
    }

    private Jwt parseJwtFromToken(final @NonNull String token) {
        try {
            return jwtService.decodeJwt(token.trim().substring(7));
        } catch (JwtException e) {
            return null;
        }
    }

    private UserDetails getUserFromJwt(final @NonNull Jwt jwt) {
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

    private Authentication getAuthenticationFromUserDetails(
            final UserDetails userDetails,
            final HttpServletRequest request
    ) {
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
