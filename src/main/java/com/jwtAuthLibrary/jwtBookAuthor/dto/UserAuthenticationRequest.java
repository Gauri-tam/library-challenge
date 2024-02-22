package com.jwtAuthLibrary.jwtBookAuthor.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationRequest {

    @NotEmpty(message = "Required Username !")
    @Email(message = "Invalid Email ! ")
    private String userName;

    @NotEmpty(message = "Required Password !")
    @Size(min = 8 , message = "Your password should be minimum 8 character !")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "Please check your password")
    private String password;
}
