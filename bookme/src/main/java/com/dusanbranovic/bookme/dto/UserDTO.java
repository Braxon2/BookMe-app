package com.dusanbranovic.bookme.dto;

import com.dusanbranovic.bookme.models.UserType;

public record UserDTO(
        Long id,
        UserType userType,
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
