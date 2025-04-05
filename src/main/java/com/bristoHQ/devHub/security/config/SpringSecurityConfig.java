package com.bristoHQ.devHub.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.bristoHQ.devHub.security.CustomSuccessHandler;
import com.bristoHQ.devHub.security.CustomerUserDetailsService;
import com.bristoHQ.devHub.security.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CustomSuccessHandler successHandler;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*")); // Change to match frontend URL
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true); // Allow cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/css/**", "/js/**", "/images/**",
                                 "/error", 
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html", "/api/v1/users/byToken" , "/login/oauth2/code/google",
                                "/api/v1/public/users/**")
                        .permitAll()
                        .requestMatchers("/api/v1/auth/public").permitAll()
                        .requestMatchers("/api/**").authenticated()

                        .requestMatchers("/api/v1/users/**")
                        .hasAnyAuthority("USER", "ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/api/v1/admins/**")
                        .hasAnyAuthority("ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/api/v1/superadmins/**").hasAnyAuthority(
                                "SUPER_ADMIN")
                        .anyRequest().authenticated()

                ).oauth2Login(login -> login
                        .successHandler(successHandler)
                        .permitAll())

                // Handle unauthorized requests with JSON response instead of redirect
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler()))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(customerUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, error) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"You need to log in first.\"}");
        };
    }
}
