package com.example.auth.domain.user.web;

import com.example.auth.domain.user.api.UserDto;
import com.example.auth.domain.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public UserResponse save(@RequestBody UserRequest userRequest){
    return toResponse(service.addUser(userRequest.toDto()));
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id){
        return toResponse(service.getUser(id));
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers().stream().map(userDto -> toResponse(userDto)).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable String id , @RequestBody UserRequest userRequest){
        return toResponse(service.updateUser(id , userRequest.toDto()));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        service.deleteUser(id);
        return "User deleted successfuly";
    }

    public UserResponse toResponse(UserDto userDto){
        return UserResponse.builder()
                .userId(userDto.getUserId())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}
