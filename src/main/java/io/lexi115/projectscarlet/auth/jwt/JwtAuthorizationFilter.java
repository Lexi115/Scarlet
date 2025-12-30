package io.lexi115.projectscarlet.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("111111");
            filterChain.doFilter(request, response);
            return;
        }
        var jwt = jwtService.decodeJwt(authHeader.trim().substring(7));
        var jwtSubject = jwt.getSubject();
        var context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null || jwtSubject == null) {
            System.out.println("222222");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var userDetails = userDetailsService.loadUserByUsername(jwtSubject);
            if (!jwtService.validateJwt(jwt, userDetails)) {
                System.out.println("3333333");
                filterChain.doFilter(request, response);
                return;
            }
            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            System.out.println("4444444");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("5555555");
        filterChain.doFilter(request, response);
    }
}
