package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.requests.BookableUnitRequestDTO;
import com.dusanbranovic.bookme.dto.requests.PropertyRequestDTO;
import com.dusanbranovic.bookme.dto.requests.ReviewRequestDTO;
import com.dusanbranovic.bookme.dto.responses.BookableUnitsResponseDTO;
import com.dusanbranovic.bookme.dto.responses.PropertyDTO;
import com.dusanbranovic.bookme.dto.responses.ReviewResponseDTO;
import com.dusanbranovic.bookme.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public List<PropertyDTO> getAll(){
        return propertyService.getAll();
    }

    @PostMapping
    public PropertyDTO addPorperty(
            @RequestBody PropertyRequestDTO dto,
            Principal principal
    ){
        return propertyService.addProperty(dto, principal.getName());
    }

    @GetMapping("/{pid}")
    public PropertyDTO getProperty(@PathVariable Long pid){
        return propertyService.getProperty(pid);
    }

    @GetMapping("/{pid}/units")
    public List<BookableUnitsResponseDTO> getAllUnits(@PathVariable Long pid){
        return propertyService.getAllUnits(pid);
    }

    @PostMapping("/{pid}/add-unit")
    public BookableUnitsResponseDTO addUnit(@PathVariable Long pid,
                                            @RequestBody BookableUnitRequestDTO dto){
        return propertyService.addUnit(pid, dto);
    }

    @PostMapping("/{pid}/reviews")
    public ReviewResponseDTO addReview(@RequestBody ReviewRequestDTO dto,
                                       @PathVariable Long pid){
        return propertyService.addReview(dto,pid);
    }

    @GetMapping("/{pid}/reviews")
    public List<ReviewResponseDTO> getReviews(@PathVariable Long pid){
        return propertyService.getReviews(pid);
    }



}
