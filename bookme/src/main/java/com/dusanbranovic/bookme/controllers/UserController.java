package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.dto.BookingResponseDTO;
import com.dusanbranovic.bookme.dto.PropertyDTO;
import com.dusanbranovic.bookme.dto.PropertyRequestDTO;
import com.dusanbranovic.bookme.dto.UserDTO;
import com.dusanbranovic.bookme.models.User;
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
    private final BookingService bookingService;

    public UserController(UserService userService, PropertyService propertyService, BookingService bookingService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/{uid}/properties")
    public PropertyDTO addPorperty(
            @RequestBody PropertyRequestDTO dto,
            @PathVariable Long uid
    ){
        return propertyService.addProperty(dto,uid);
    }

    @PostMapping("/{user_id}/book/{unit_id}")
    public BookingResponseDTO bookAUnit(
            @PathVariable Long user_id,
            @PathVariable Long unit_id
    ){
        return bookingService.bookAUnit(user_id,unit_id);
    }


//    @PostMapping
//    public User addUser(@RequestBody UserDTO dto){
//        return userService.addUser(dto);
//    }
}
