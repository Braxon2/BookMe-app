package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.service.UnitFascillityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unit-fascilities")
public class UnitFascillityController {

    private final UnitFascillityService unitFascillityService;

    public UnitFascillityController(UnitFascillityService unitFascillityService) {
        this.unitFascillityService = unitFascillityService;
    }
}
