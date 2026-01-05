package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.service.BookableUnitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class BookableUnitController {

    private final BookableUnitService bookableUnitService;

    public BookableUnitController(BookableUnitService bookableUnitService) {
        this.bookableUnitService = bookableUnitService;
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


}
