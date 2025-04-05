package com.bristoHQ.devHub.security.jwt;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bristoHQ.devHub.security.CustomerUserDetailsService;
import com.bristoHQ.devHub.services.user.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;

    private final UserService userService;

    private final CustomerUserDetailsService customerUserDetailsService;

    public JwtAuthenticationFilter( @Lazy JwtUtilities jwtUtilities, @Lazy UserService userService,
    @Lazy  CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.userService = userService;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtilities.getToken(request);

        if (userService.isTokenBlacklisted(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null && jwtUtilities.validateToken(token)) {
            String email = jwtUtilities.extractUsername(token);

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                log.info("authenticated user with email :{}", email);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);
    }

}
