package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.service.BookableUnitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/units")
public class BookableUnitController {

    private final BookableUnitService bookableUnitService;

    public BookableUnitController(BookableUnitService bookableUnitService) {
        this.bookableUnitService = bookableUnitService;
    }
}
