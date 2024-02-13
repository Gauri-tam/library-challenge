package com.jwtAuthLibrary.jwtBookAuthor.dto;

import com.jwtAuthLibrary.jwtBookAuthor.enumerate.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Roles roles;
}
