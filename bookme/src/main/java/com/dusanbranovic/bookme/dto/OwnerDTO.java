package com.dusanbranovic.bookme.dto;

import com.dusanbranovic.bookme.models.UserType;

public record OwnerDTO(
        Long id,
        UserType userType
) {
}
