package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.repository.ReviewRepository;
import com.dusanbranovic.bookme.repository.UnitFascilityRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitFascillityService {

    private final UnitFascilityRepository unitFascilityRepository;

    public UnitFascillityService(UnitFascilityRepository unitFascilityRepository) {
        this.unitFascilityRepository = unitFascilityRepository;
    }
}
