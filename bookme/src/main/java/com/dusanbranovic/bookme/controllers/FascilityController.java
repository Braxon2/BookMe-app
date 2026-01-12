package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.dto.FascilityRequestDTO;
import com.dusanbranovic.bookme.dto.FascilityResponseDTO;
import com.dusanbranovic.bookme.service.AddonService;
import com.dusanbranovic.bookme.service.FascilityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fascilities")
public class FascilityController {

    private final FascilityService fascilityService;

    public FascilityController(FascilityService fascilityService) {
        this.fascilityService = fascilityService;
    }

    @PostMapping
    public FascilityResponseDTO addFascility(@RequestBody FascilityRequestDTO dto){
        return fascilityService.addFascility(dto);
    }
}
