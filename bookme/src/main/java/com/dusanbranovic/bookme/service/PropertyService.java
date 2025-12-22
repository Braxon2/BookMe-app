package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.repository.PropertyImageRepository;
import com.dusanbranovic.bookme.repository.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
}
