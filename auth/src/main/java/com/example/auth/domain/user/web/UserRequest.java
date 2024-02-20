package com.example.auth.domain.user.web;

import com.example.auth.domain.user.api.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private int role;


    UserDto toDto(){
        return UserDto.builder()
                .firstname(this.firstname)
                .lastname(this.lastname)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }
}
