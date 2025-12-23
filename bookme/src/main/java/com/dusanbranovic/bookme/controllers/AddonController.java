package com.dusanbranovic.bookme.controllers;

import com.dusanbranovic.bookme.service.AddonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addons")
public class AddonController {

    private final AddonService addonService;

    public AddonController(AddonService addonService) {
        this.addonService = addonService;
    }
}
