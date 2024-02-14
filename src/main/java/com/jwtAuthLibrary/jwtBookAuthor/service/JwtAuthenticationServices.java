package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserAuthenticationRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserAuthenticationResponse;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterResponse;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Token;
import com.jwtAuthLibrary.jwtBookAuthor.entity.User;
import com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles;
import com.jwtAuthLibrary.jwtBookAuthor.enumerate.TokenType;
import com.jwtAuthLibrary.jwtBookAuthor.exceptionclass.HeaderIsNotPresentException;
import com.jwtAuthLibrary.jwtBookAuthor.exceptionclass.TokenIsNotValidException;
import com.jwtAuthLibrary.jwtBookAuthor.exceptionclass.UserIsNotPresentException;
import com.jwtAuthLibrary.jwtBookAuthor.repository.TokenRepository;
import com.jwtAuthLibrary.jwtBookAuthor.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationServices {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtServices jwtServices;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenRepository tokenRepository;

     // This method is use to get register the user;
    public UserRegisterResponse registration(UserRegisterRequest request, HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null){
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .roles(Roles.USER)
                    .build();
            userRepository.save(user);
            return UserRegisterResponse.builder()
                    .userName(request.getFirstName()+" "+request.getLastName())
                    .userEmail(request.getEmail())
                    .message("User Created Successfully!")
                    .build();
        }
        return UserRegisterResponse.builder()
                .userName(request.getFirstName()+" "+request.getLastName())
                .userEmail(request.getEmail())
                .message("Authorization must Not Take any Username and Password!")
                .build();
    }

    public UserRegisterResponse registerSuperAdmin(UserRegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Roles.SUPER_ADMIN)
                .build();
        userRepository.save(user);
        return UserRegisterResponse.builder()
                .userName(request.getFirstName()+" "+request.getLastName())
                .userEmail(request.getEmail())
                .message("SUPER_ADMIN Created Successfully!")
                .build();
    }

     // This method is use to check if user is Authenticate or Not
    public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getUserName()).orElseThrow();
        var jwtToken = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(user);
        revokedAllToken(user);
        saveUserToken(user, jwtToken);
        return UserAuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    // whenever the user get authenticated it will generated
    // token and it will get Stored inside the Token Entity
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    // it will check that token is valid or not
    private void revokedAllToken(User user){
        var isValidUser = tokenRepository.findTokenByUserId(user.getUserId());
        if (isValidUser.isEmpty()){ return;}
        isValidUser.forEach( t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(isValidUser);
    }

    // generate access token by using refresh token.
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new HeaderIsNotPresentException("Check you Header!");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtServices.extractUserName(refreshToken);
        if (userEmail != null){
            var user = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtServices.isValidToken(refreshToken,user)){
                var accessToken = jwtServices.generateToken(user);
                revokedAllToken(user);
                saveUserToken(user, accessToken);
                var authResponse = UserAuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse );
            }else {
                throw new TokenIsNotValidException("your current token is not valid!");
            }
        }else {
            throw new UserIsNotPresentException("Username is Not Present!");
        }
    }
}
