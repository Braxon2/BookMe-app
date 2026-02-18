package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.dto.responses.PropertyDTO;
import com.dusanbranovic.bookme.dto.responses.UserDTO;

import com.dusanbranovic.bookme.service.BookingService;
import com.dusanbranovic.bookme.service.PropertyService;
import com.dusanbranovic.bookme.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PropertyService propertyService;

    public UserController(
            UserService userService,
            PropertyService propertyService
    ) {
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userID}/properties")
    public List<PropertyDTO> getProperties(@PathVariable Long userID){
        return propertyService.getPropertiesFromOwner(userID);
    }

}
