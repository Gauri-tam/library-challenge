package com.jwtAuthLibrary.jwtBookAuthor.config;

import com.jwtAuthLibrary.jwtBookAuthor.jwt.JwtAuthenticationEntryPoint;
import com.jwtAuthLibrary.jwtBookAuthor.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Permission.*;
import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    private static final String[] URL = {
            "/api/v2/library/**",  // for creating book and author;
            "/api/v1/auth/library/**",  // post all register, authenticate, refresh token;
            "/api/v1/library/super-admin/**" // get all operations of super admin
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers(URL).permitAll()
                        // all admin related operations + also super admin can use it.
                        .requestMatchers( "/api/v1/library/admin/**").hasAnyRole(ADMIN.name(), SUPER_ADMIN.name())
                        .requestMatchers(GET,"/api/v1/library/admin/**").hasAnyAuthority(ADMIN_READ.name(), SUPER_ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/library/admin/**").hasAnyAuthority(ADMIN_CREATE.name(), SUPER_ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/library/admin/**").hasAnyAuthority(ADMIN_UPDATE.name(), SUPER_ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/library/admin/**").hasAnyAuthority(ADMIN_DELETE.name(), SUPER_ADMIN_DELETE.name())
                        // only get operations for user
                        .requestMatchers("/api/v1/library/user/**").hasRole(USER.name())
                        .requestMatchers(GET, "/api/v1/library/user/**").hasAuthority(USER_READ.name())
                        .anyRequest().authenticated())
                // response messages
                .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Create a Session Management as STATELESS
                .sessionManagement(session->session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                // add Authentication filter
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                // for logout by using token of user
                .logout(logout->logout
                        .logoutUrl("/api/v1/library/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }
}
