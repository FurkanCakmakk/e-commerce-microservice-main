package com.example.auth.domain.user.impl;

import com.example.auth.domain.user.api.UserDto;
import com.example.auth.domain.user.api.UserService;
import com.example.auth.domain.user.web.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final int ADMIN_ROLE = 0;
    public static final int SUPER_ADMIN_ROLE = 1;
    public static final int SHOP_ADMIN_ROLE = 2;

    private final UserRepository repository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = toEntity(userDto);
        user = repository.save(user);
        return toDto(user);
    }

    @Override
    public UserDto getUser(String id) {
        return toDto(repository.findById(Long.valueOf(id)).get());
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        User user = repository.findById(Long.valueOf(id)).get();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user = repository.save(user);
        return toDto(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = repository.findById(Long.valueOf(id)).get();
        repository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream().map(user -> toDto(user)).collect(Collectors.toList());
    }

    // Response'tan önceki metod
    public UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .role(user.getRole())
                .build();
    }


    // Repository'e gitmeden önce çalışacak olan metod
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
}
