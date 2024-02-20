package com.example.auth.domain.user.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private long userId;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private int role;


}
