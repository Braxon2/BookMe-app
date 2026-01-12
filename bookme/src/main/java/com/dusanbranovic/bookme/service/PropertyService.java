package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.BookableUnitRequestDTO;
import com.dusanbranovic.bookme.dto.BookableUnitsResponseDTO;
import com.dusanbranovic.bookme.dto.PropertyDTO;
import com.dusanbranovic.bookme.dto.PropertyRequestDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.mappers.BookableUnitMapper;
import com.dusanbranovic.bookme.mappers.PropertyMapper;
import com.dusanbranovic.bookme.mappers.PropertyTypeMapper;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.Property;
import com.dusanbranovic.bookme.models.PropertyType;
import com.dusanbranovic.bookme.models.User;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PropertyRepository;
import com.dusanbranovic.bookme.repository.PropertyTypeRepository;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyTypeRepository propertyTypeRepository;

    private final PropertyMapper propertyMapper;
    private final PropertyTypeMapper propertyTypeMapper;
    private final BookableUnitMapper bookableUnitMapper;
    private final BookableUnitRepository bookableUnitRepository;

    public PropertyService(
            PropertyRepository propertyRepository,
            UserRepository userRepository, PropertyTypeRepository propertyTypeRepository,
            PropertyMapper propertyMapper,
            PropertyTypeMapper propertyTypeMapper, BookableUnitMapper bookableUnitMapper,
            BookableUnitRepository bookableUnitRepository
    ) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.propertyMapper = propertyMapper;
        this.propertyTypeMapper = propertyTypeMapper;
        this.bookableUnitMapper = bookableUnitMapper;
        this.bookableUnitRepository = bookableUnitRepository;
    }

    public List<PropertyDTO> getAll() {
        return propertyRepository.findAll()
                .stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PropertyDTO getProperty(Long pid) {
        Optional<Property> optionalProperty = propertyRepository.findById(pid);

        if(optionalProperty.isEmpty()) {
            throw new EntityNotFoundException("Property with id " + pid + " not found");
        }


            return propertyMapper.toDTO(
                    optionalProperty.get()
            );
    }

    public PropertyDTO addProperty(PropertyRequestDTO dto, String email) {

        User owner = userRepository.findByEmail(email).orElseThrow();



        Optional<PropertyType> optionalPropertyType = propertyTypeRepository.findById(dto.propertyTypeDTO().id());
        if(!optionalPropertyType.isPresent()){
            return null;
        }

        Property property = new Property();
        property.setOwner(owner);
        property.setPropertyType(optionalPropertyType.get());
        property.setName(dto.name());
        property.setDescription(dto.description());
        property.setCountry(dto.country());
        property.setCity(dto.city());
        property.setAddress(dto.address());
        property.setHouseRules(dto.houseRules());
        property.setImportantInfo(dto.importantInfo());
        System.out.println(property);
        return propertyMapper.toDTO(propertyRepository.save(property));
    }


    public List<BookableUnitsResponseDTO> getAllUnits(Long pid) {
        Optional<Property> optionalProperty = propertyRepository.findById(pid);

        if(optionalProperty.isEmpty()) {
            throw new EntityNotFoundException("Property with id " + pid + " not found");
        }

        Property property = optionalProperty.get();
        return property.getUnits().
                stream().
                map(bookableUnitMapper::toDTO)
                .collect(Collectors.toList());

    }

    public BookableUnitsResponseDTO addUnit(Long pid, BookableUnitRequestDTO dto) {
        Optional<Property> optionalProperty = propertyRepository.findById(pid);

        if(optionalProperty.isEmpty()) {
            throw new EntityNotFoundException("Property with id " + pid + " not found");
        }

        Property property = optionalProperty.get();

        BookableUnit unit = bookableUnitMapper.toEntity(dto,property);

        BookableUnit savedUnit = bookableUnitRepository.save(unit);
        List<BookableUnit> units = property.getUnits();
        units.add(unit);

        return bookableUnitMapper.toDTO(savedUnit);

    }
}
