package com.jwtAuthLibrary.jwtBookAuthor.config;

import com.jwtAuthLibrary.jwtBookAuthor.jwt.JwtAuthenticationEntryPoint;
import com.jwtAuthLibrary.jwtBookAuthor.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Permission.*;
import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles.ADMIN;
import static com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles.SUPER_ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers("/api/v1/library/**").permitAll()
                        .requestMatchers("/api/v1/auth/library/**").permitAll()
                        .requestMatchers("/api/v1/library/super-admin/**").hasRole(SUPER_ADMIN.name())
                        .requestMatchers(GET,"/api/v1/library/super-admin/**").hasAuthority(SUPER_ADMIN_READ.name())
                        .requestMatchers(POST,"/api/v1/library/super-admin/**").hasAuthority(SUPER_ADMIN_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/library/super-admin/**").hasAuthority(SUPER_ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/library/super-admin/**").hasAuthority(SUPER_ADMIN_DELETE.name())
                        .requestMatchers( "/api/v1/library/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"/api/v1/library/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/library/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/library/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/library/admin/**").hasAuthority(ADMIN_DELETE.name())
                        .requestMatchers("/api/v1/library/user/**").hasRole(USER_READ.name())
                        .requestMatchers(GET, "/api/v1/library/user/**").hasAuthority(USER_READ.name())
                        .anyRequest().authenticated())
                .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session->session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .logout(logout->logout
                        .logoutUrl("/api/v1/library/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }
}
