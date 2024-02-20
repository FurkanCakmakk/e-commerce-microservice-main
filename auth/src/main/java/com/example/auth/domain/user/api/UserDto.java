package com.example.auth.domain.user.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int role;


}
