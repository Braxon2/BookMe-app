package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.BookingRequestDTO;
import com.dusanbranovic.bookme.dto.BookingResponseDTO;
import com.dusanbranovic.bookme.dto.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.service.BookableUnitService;
import com.dusanbranovic.bookme.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class BookableUnitController {

    private final BookableUnitService bookableUnitService;

    private final BookingService bookingService;

    public BookableUnitController(BookableUnitService bookableUnitService, BookingService bookingService) {
        this.bookableUnitService = bookableUnitService;
        this.bookingService = bookingService;
    }

    @PostMapping("/{unit_id}/add-price")
    public PeriodPriceResponseDTO addPeriodPrice(
            @PathVariable Long unit_id,
            @RequestBody PeriodPriceRequestDTO periodPriceDTO
    ){
        return bookableUnitService.addPeriodPrice(unit_id,periodPriceDTO);
    }

    @GetMapping("/{unit_id}/period-prices")
    public List<PeriodPriceResponseDTO> getPeriodPrices(
            @PathVariable Long unit_id
            ){
        return bookableUnitService.getPeriodPrices(unit_id);
    }


    @PostMapping("/{unit_id}/book")
    public BookingResponseDTO bookAUnit(
            @PathVariable Long unit_id,
            @RequestBody BookingRequestDTO bookingRequestDTO
    ){
        return bookingService.bookAUnit(unit_id,bookingRequestDTO);
    }


}
