package com.example.basket.domain.library;


import com.example.basket.domain.basket.api.user.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AuthCallableFeignClient {

     @GetMapping("/users/{id}")
     UserDto getUserById(@PathVariable String id);
}
