package com.dusanbranovic.bookme.mappers;

import com.dusanbranovic.bookme.dto.PropertyDTO;
import com.dusanbranovic.bookme.models.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

    private final UserMapper userMapper;
    private final PropertyTypeMapper propertyTypeMapper;

    public PropertyMapper(
            UserMapper userMapper,
            PropertyTypeMapper propertyTypeMapper
    ) {
        this.userMapper = userMapper;
        this.propertyTypeMapper = propertyTypeMapper;
    }

    public PropertyDTO toDTO(Property property){
        return new PropertyDTO(property.getId(),
                userMapper.toDTO(property.getOwner()),
                propertyTypeMapper.toDTO(property.getPropertyType()),
                property.getName(),
                property.getDescription(),
                property.getCountry(),
                property.getCity(),
                property.getAddress(),
                property.getHouseRules(),
                property.getImportantInfo());
    }

//    public Property toProperty(PropertyDTO dto){
//        return new Property(dto.id(),
//                userMapper.toUser(dto.ownerDTO()),
//                propertyTypeMapper.toPropertyType(dto.propertyTypeDTO()),
//                dto.name(),
//                dto.description(),
//                dto.country(),
//                dto.city(),
//                dto.address(),
//                dto.houseRules(),
//                dto.importantInfo());
//    }
}
