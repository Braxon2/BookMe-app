package com.dusanbranovic.bookme.dto;

import com.dusanbranovic.bookme.models.UserType;

public record UserDTO(
        UserType userType,
        String email,
        String firstName,
        String lastName,
        String password,
        String phoneNumber
) {
}
