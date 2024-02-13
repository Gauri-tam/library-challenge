package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterResponse;
import com.jwtAuthLibrary.jwtBookAuthor.entity.User;
import com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles;
import com.jwtAuthLibrary.jwtBookAuthor.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Value("${security.jwt.user-name}")
    private String username;

    @Value("${security.jwt.password}")
    private String password;

    // register for Admin only
    public UserRegisterResponse registerAdmin(UserRegisterRequest request, HttpServletRequest req) {
        assert req != null;
        String authHeader = req.getHeader("Authorization");

        // First it will check the authHeader is not null and also it will check the Authentication will start with Basic_;
        if (authHeader != null && authHeader.startsWith("Basic ")){

            //Basic c3VwZXJhZG1pbjpzdXBlcmFkbWlu

            // This Will take -> c3VwZXJhZG1pbjpzdXBlcmFkbWlu
            String usernamePassword = new String(Base64.decodeBase64(authHeader.substring(6)));

            // and convert it into the username:password
            int separatorIndex = usernamePassword.indexOf(':');

            //it will take 0 string , that's username
            var userName = usernamePassword.substring(0, separatorIndex);

            //it will take 1 string , that's password
            var passWord = usernamePassword.substring(separatorIndex + 1);

            System.out.println("Username : " +userName);
            System.out.println("Password : " +passWord);

            if(userName.equals(username) && passWord.equals(password) ) {
                var user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(Roles.ADMIN)
                        .build();
                userRepository.save(user);
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName() + " " + request.getLastName())
                        .userEmail(request.getEmail())
                        .message("Admin Created Successfully!")
                        .build();
            } else {
                // Unauthorized access
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName() + " " + request.getLastName())
                        .userEmail(request.getEmail())
                        .message("Access Denied! Check your credentials")
                        .build();
            }
        } else {
            // No or invalid Authorization header
            return UserRegisterResponse.builder()
                    .userName(request.getFirstName() + " " + request.getLastName())
                    .userEmail(request.getEmail())
                    .message("Access Denied! Authorization header missing or invalid")
                    .build();
        }
    }
}

