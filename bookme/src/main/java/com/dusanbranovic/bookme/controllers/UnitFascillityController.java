package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.FascilityRequestDTO;
import com.dusanbranovic.bookme.dto.FascilityResponseDTO;
import com.dusanbranovic.bookme.dto.UnitFascilityRequestDTO;
import com.dusanbranovic.bookme.dto.UnitFascilityResponseDTO;
import com.dusanbranovic.bookme.service.UnitFascillityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unit-fascilities")
public class UnitFascillityController {

    private final UnitFascillityService unitFascillityService;

    public UnitFascillityController(UnitFascillityService unitFascillityService) {
        this.unitFascillityService = unitFascillityService;
    }

    @PostMapping
    public UnitFascilityResponseDTO addFascility(@RequestBody UnitFascilityRequestDTO dto){
        return unitFascillityService.addUnitFascility(dto);
    }
}
