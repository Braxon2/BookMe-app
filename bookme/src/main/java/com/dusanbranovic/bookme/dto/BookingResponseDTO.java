package com.dusanbranovic.bookme.dto;

import com.dusanbranovic.bookme.models.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponseDTO(
        BookableUnitsResponseDTO bookableUnit,
        UserDTO guest,
        Double totalPrice,
        LocalDate createdAt,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        BookingStatus status
) {
}
