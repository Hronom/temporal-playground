package com.github.hronom.temporalplaygroundusers.controllers;

import com.github.hronom.temporalplaygroundusers.controllers.dto.UserDto;
import com.github.hronom.temporalplaygroundusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public void createUser(
            @RequestBody UserDto userDto
    ) {
        userService.createUser(userDto);
    }
}
