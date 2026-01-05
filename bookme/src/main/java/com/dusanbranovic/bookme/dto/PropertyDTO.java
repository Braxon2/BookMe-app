package com.dusanbranovic.bookme.dto;


public record PropertyDTO(
        Long id,
        UserDTO ownerDTO,
        PropertyTypeDTO propertyTypeDTO,
        String name,
        String description,
        String country,
        String city,
        String address,
        String houseRules,
        String importantInfo
) {
}
