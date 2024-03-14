package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.dto.UserAuthenticationRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserAuthenticationResponse;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterResponse;
import com.jwtAuthLibrary.jwtBookAuthor.service.JwtAuthenticationServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/library")
public class JwtAuthenticationController {

    private final JwtAuthenticationServices jwtAuthenticationServices;

    // http://localhost:8080/api/v1/auth/library/register

    // Use to Register the User
    @PostMapping("/registerUser")
    public ResponseEntity<UserRegisterResponse> registration(@Valid @RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(jwtAuthenticationServices.registration(request, req));
    }

    // Use to Authenticate The User
    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@Valid @RequestBody UserAuthenticationRequest request) throws Exception{
        return ResponseEntity.ok(jwtAuthenticationServices.authenticate(request));
    }

    // generate the token by using refresh token
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        jwtAuthenticationServices.refreshToken(request, response);
    }

    @PostMapping("/registerSuperAdmin")
    public ResponseEntity<UserRegisterResponse> registrationSuperAdmin(@RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(jwtAuthenticationServices.registerSuperAdmin(request, req));
    }
}
