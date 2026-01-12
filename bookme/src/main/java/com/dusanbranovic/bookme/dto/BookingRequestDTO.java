package com.dusanbranovic.bookme.dto;

import java.time.LocalDate;


public record BookingRequestDTO(
        LocalDate start_date,
        LocalDate end_date
) {
}
