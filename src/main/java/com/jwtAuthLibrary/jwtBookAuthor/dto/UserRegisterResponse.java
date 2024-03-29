package com.jwtAuthLibrary.jwtBookAuthor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {

    private String userName;
    private String userEmail;
    private String message ;
}
