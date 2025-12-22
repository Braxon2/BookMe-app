package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.repository.AddonRepository;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class BookableUnitService {

    private final BookableUnitRepository bookableUnitRepository;

    public BookableUnitService(BookableUnitRepository bookableUnitRepository) {
        this.bookableUnitRepository = bookableUnitRepository;
    }
}
