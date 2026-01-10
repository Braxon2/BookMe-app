package com.dusanbranovic.bookme.mappers;

import com.dusanbranovic.bookme.dto.UserDTO;
import com.dusanbranovic.bookme.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){

        return new UserDTO(
                user.getId(),
                user.getRole(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber());

    }

    public User toEntity(UserDTO dto){

        User user = new User();
        user.setId(dto.id());
        user.setRole(dto.userType());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setPhoneNumber(dto.phoneNumber());
        return user;
    }

}
