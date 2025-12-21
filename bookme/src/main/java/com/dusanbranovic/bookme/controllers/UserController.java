package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.dto.UserDTO;
import com.dusanbranovic.bookme.models.User;
import com.dusanbranovic.bookme.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody UserDTO dto){
        return userService.addUser(dto);
    }
}
