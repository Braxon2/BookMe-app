package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.repository.AddonRepository;
import org.springframework.stereotype.Service;

@Service
public class AddonService {

    private final AddonRepository addonRepository;

    public AddonService(AddonRepository addonRepository) {
        this.addonRepository = addonRepository;
    }
}
