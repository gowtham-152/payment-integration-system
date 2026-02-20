package com.learn.stripepay.userservice.controller;

import com.learn.stripepay.userservice.dto.UserRequest;
import com.learn.stripepay.userservice.dto.UserResponse;
import com.learn.stripepay.userservice.entity.User;
import com.learn.stripepay.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }


}
