package com.dusanbranovic.bookme.integrations.repository;

import com.dusanbranovic.bookme.models.Property;
import com.dusanbranovic.bookme.repository.PropertyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    @DisplayName("Test if the DB returns property correctly")
    void findById(){
        Long id = 1L;
        Optional<Property> optionalProperty = propertyRepository.findById(id);

        assertTrue(optionalProperty.isPresent());
        assertEquals(id,optionalProperty.get().getId());
    }



    @Test
    void saveTest(){
        Property property = new Property();
//        property.setPropertyType(type1);
//        property.setOwner(owner1);
        property.setName("Sunny Hotel");
        property.setCity("Belgrade");
        property.setCountry("Serbia");
        property.setAddress("Main Street 1");
        property.setHouseRules("N/A");
        property.setDescription("Sunny day in Philadelphia but on Serbian");
        property.setImportantInfo("No smoking...");


    }

}