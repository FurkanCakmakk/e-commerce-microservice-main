package com.example.basket.domain.basket.api.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int role;

}
