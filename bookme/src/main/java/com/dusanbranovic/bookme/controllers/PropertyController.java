package com.dusanbranovic.bookme.controllers;


import com.dusanbranovic.bookme.dto.BookableUnitRequestDTO;
import com.dusanbranovic.bookme.dto.BookableUnitsResponseDTO;
import com.dusanbranovic.bookme.dto.PropertyDTO;
import com.dusanbranovic.bookme.dto.PropertyRequestDTO;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.Property;
import com.dusanbranovic.bookme.service.PropertyService;
import org.springframework.web.bind.annotation.*;

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



//    @GetMapping("{pid}")
//    public List<BookableUnit> getAllUnitsFromProperty(@PathVariable Long pid){
//        return propertyService.getAllUnitsFromProperty(pid);
//    }

}
