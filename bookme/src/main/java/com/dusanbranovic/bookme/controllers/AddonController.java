package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.dto.requests.AddonRequestDTO;
import com.dusanbranovic.bookme.dto.requests.PeriodPriceAddonRequestDTO;
import com.dusanbranovic.bookme.dto.responses.AddonPeriodPriceResponseDTO;
import com.dusanbranovic.bookme.dto.responses.AddonResponseDTO;
import com.dusanbranovic.bookme.service.AddonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addons")
public class AddonController {

    private final AddonService addonService;

    public AddonController(AddonService addonService) {
        this.addonService = addonService;
    }



    @GetMapping
    public List<AddonResponseDTO> getAllAddons(){
        return addonService.getAllAddons();
    }

    @PostMapping("/{aid}/add-price")
    public AddonPeriodPriceResponseDTO addAddonPeriodPrice(@RequestBody PeriodPriceAddonRequestDTO dto,
                                                           @PathVariable Long aid
    ){
        return addonService.addAddonPeriodPrice(aid, dto);
    }


}
