package com.example.auth.domain.user.api;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);
    UserDto getUser(String id);
    UserDto updateUser(String id , UserDto userDto);
    void deleteUser(String  id);
    List<UserDto> getAllUsers();
}
