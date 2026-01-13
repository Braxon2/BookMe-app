package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.requests.AddonRequestDTO;
import com.dusanbranovic.bookme.dto.requests.BookingRequestDTO;
import com.dusanbranovic.bookme.dto.responses.AddonResponseDTO;
import com.dusanbranovic.bookme.dto.responses.BookingResponseDTO;
import com.dusanbranovic.bookme.dto.requests.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.responses.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.service.AddonService;
import com.dusanbranovic.bookme.service.BookableUnitService;
import com.dusanbranovic.bookme.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class BookableUnitController {

    private final BookableUnitService bookableUnitService;
    private final AddonService addonService;

    private final BookingService bookingService;

    public BookableUnitController(BookableUnitService bookableUnitService,
                                  AddonService addonService,
                                  BookingService bookingService
    ) {
        this.bookableUnitService = bookableUnitService;
        this.addonService = addonService;
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

    @PostMapping("/{unitId}/addons")
    public AddonResponseDTO addAddon(@PathVariable Long unitId,
                                     @RequestBody AddonRequestDTO dto){
        return addonService.addAddon(unitId,dto);
    }





}
