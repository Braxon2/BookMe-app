package com.dusanbranovic.bookme.dto.responses;


import java.util.List;

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
        String importantInfo,
        List<FascilityResponseDTO> fascilitiesDTO
) {
}
