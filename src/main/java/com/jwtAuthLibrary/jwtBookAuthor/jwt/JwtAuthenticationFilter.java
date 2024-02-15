package com.jwtAuthLibrary.jwtBookAuthor.jwt;

import com.jwtAuthLibrary.jwtBookAuthor.exceptionclass.TokenIsNotValidException;
import com.jwtAuthLibrary.jwtBookAuthor.exceptionclass.UserIsNotPresentException;
import com.jwtAuthLibrary.jwtBookAuthor.repository.TokenRepository;
import com.jwtAuthLibrary.jwtBookAuthor.service.JwtServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
        return;
        }
        token = authHeader.substring(7);
        userEmail = jwtServices.extractUserName(token);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid1 = tokenRepository
                    .findUserByToken(token)
                    .map(token1 -> !token1.isRevoked() && !token1.isExpired())
                    .orElse(false);
            if (jwtServices.isValidToken(token,userDetails) && isTokenValid1){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                throw new TokenIsNotValidException("Token is Not Valid!");
            }
        }
        else {
            throw new UserIsNotPresentException("User Not Present in Database");
        }
        filterChain.doFilter(request, response);
    }
}
