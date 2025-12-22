package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.repository.ContactPersonRepository;
import com.dusanbranovic.bookme.repository.FasiliityRepository;
import org.springframework.stereotype.Service;

@Service
public class FascilityService {

    private final FasiliityRepository fasiliityRepository;

    public FascilityService(FasiliityRepository fasiliityRepository) {
        this.fasiliityRepository = fasiliityRepository;
    }
}
