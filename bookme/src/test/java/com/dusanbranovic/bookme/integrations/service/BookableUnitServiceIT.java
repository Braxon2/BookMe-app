package com.dusanbranovic.bookme.integrations.service;

import com.dusanbranovic.bookme.dto.requests.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.responses.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PeriodPriceRepository;
import com.dusanbranovic.bookme.service.BookableUnitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BookableUnitServiceIT {

    @Autowired
    private BookableUnitRepository bookableUnitRepository;

    @Autowired
    private PeriodPriceRepository periodPriceRepository;

    @Autowired
    private BookableUnitService bookableUnitService;

    @Test
    @DisplayName("Should sucesfully save a period price")
    void addPeriodPrice(){

        BookableUnit testUnit = new BookableUnit();
        bookableUnitRepository.save(testUnit);
        LocalDate start = LocalDate.of(2026,1,1);
        LocalDate end = LocalDate.of(2026,3,8);
        PeriodPriceRequestDTO dto = new PeriodPriceRequestDTO(34,start,end,"winter");

        PeriodPriceResponseDTO result = bookableUnitService.addPeriodPrice(testUnit.getId(),dto);

        assertNotNull(result);
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(result.bookableUnitsResponseDTO().id());
        assertEquals(testUnit.getId(),optionalBookableUnit.get().getId());
    }

    @Test
    @DisplayName("Should throw Exception for Unit not found")
    void addPeriodPrice_UnitNotFound(){

        LocalDate start = LocalDate.of(2026,1,1);
        LocalDate end = LocalDate.of(2026,3,8);
        PeriodPriceRequestDTO dto = new PeriodPriceRequestDTO(34.50,start,end,"winter");

        assertThrows(EntityNotFoundException.class, () -> bookableUnitService.addPeriodPrice(10L,dto));
    }
}
