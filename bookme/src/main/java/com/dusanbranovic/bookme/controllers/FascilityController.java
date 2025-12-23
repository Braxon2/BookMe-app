package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.service.AddonService;
import com.dusanbranovic.bookme.service.FascilityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fascilities")
public class FascilityController {

    private final FascilityService fascilityService;

    public FascilityController(FascilityService fascilityService) {
        this.fascilityService = fascilityService;
    }
}
