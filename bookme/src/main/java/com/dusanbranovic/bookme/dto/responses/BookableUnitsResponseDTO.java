package com.dusanbranovic.bookme.dto.responses;

public record BookableUnitsResponseDTO(
        Long id,
        int maxCapacity,
        double squareMeters,
        int singleBeds,
        int doubleBeds,
        int maxAdultCapacity,
        int maxKidsCapacity,
        String name
) {
}
