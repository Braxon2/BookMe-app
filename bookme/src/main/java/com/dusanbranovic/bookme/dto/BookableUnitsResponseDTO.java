package com.dusanbranovic.bookme.dto;

public record BookableUnitsResponseDTO(
        Long id,
        int maxCapacity,
        double squareMeters,
        int totalUnits,
        int singleBeds,
        int doubleBeds,
        int maxAdultCapacity,
        int maxKidsCapacity,
        String name
) {
}
