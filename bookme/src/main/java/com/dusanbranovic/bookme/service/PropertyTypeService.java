package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.PropertyTypeDTO;
import com.dusanbranovic.bookme.models.PropertyType;
import com.dusanbranovic.bookme.repository.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }


    public List<PropertyType> getAll() {
        return propertyTypeRepository.findAll();
    }

    public PropertyType addType(PropertyTypeDTO dto) {
        PropertyType propertyType = new PropertyType();
        propertyType.setName(dto.name());
        return propertyTypeRepository.save(propertyType);
    }
}
