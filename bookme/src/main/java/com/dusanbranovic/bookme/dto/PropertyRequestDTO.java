package com.dusanbranovic.bookme.dto;

public record PropertyRequestDTO(
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
