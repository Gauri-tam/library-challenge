package com.jwtAuthLibrary.jwtBookAuthor.dto;

import com.jwtAuthLibrary.jwtBookAuthor.enmurate.Roles;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = "Not Accepting Empty Filed !")
    @Size(min = 3, max = 20, message = "Your First Name should be in min 3 to max 20 characters !")
    private String firstName;

    @NotEmpty(message = "Not Accepting Empty Filed !")
    @Size(min = 3, max = 20, message = "your Last Name should be in min 3 to max 20 characters !")
    private String lastName;

    @NotEmpty(message = "Not Accepting Empty Filed !")
    @Email(message = "Invalid Email !")
    private String email;

    @NotEmpty(message = "Not Accepting Empty Filed !")
    @Size(min = 8 , message = "your password should be minimum 5 character !")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "Invalid Password !")
    private String password;
    private Roles roles;
}
