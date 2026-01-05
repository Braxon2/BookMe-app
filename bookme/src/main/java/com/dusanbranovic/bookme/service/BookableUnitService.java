package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.mappers.BookableUnitMapper;
import com.dusanbranovic.bookme.mappers.PeriodPriceMapper;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.PeriodPrice;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PeriodPriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookableUnitService {

    private final BookableUnitRepository bookableUnitRepository;
    private final PeriodPriceRepository periodPriceRepository;

    private final BookableUnitMapper bookableUnitMapper;
    private final PeriodPriceMapper periodPriceMapper;

    public BookableUnitService(
            BookableUnitRepository bookableUnitRepository,
            PeriodPriceRepository periodPriceRepository,
            BookableUnitMapper bookableUnitMapper,
            PeriodPriceMapper periodPriceMapper
    ) {
        this.bookableUnitRepository = bookableUnitRepository;
        this.periodPriceRepository = periodPriceRepository;
        this.bookableUnitMapper = bookableUnitMapper;
        this.periodPriceMapper = periodPriceMapper;
    }

    public PeriodPriceResponseDTO addPeriodPrice(
            Long unitId,
            PeriodPriceRequestDTO periodPriceDTO
    ) {
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalBookableUnit.isEmpty()) {
            throw new EntityNotFoundException("Unit with id " + unitId + " not found");
        }


        BookableUnit unit = optionalBookableUnit.get();


        PeriodPrice periodPrice = periodPriceMapper.toEntity(periodPriceDTO, unit);


        PeriodPrice savedPeriodPrice = periodPriceRepository.save(periodPrice);

        return periodPriceMapper.toDTO(savedPeriodPrice);


    }

    public List<PeriodPriceResponseDTO> getPeriodPrices(Long unitId) {
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalBookableUnit.isEmpty()) {
            throw new EntityNotFoundException("Unit with id " + unitId + " not found");
        }

        BookableUnit unit = optionalBookableUnit.get();

        return unit.getPeriodPriceList().
                stream().
                map(periodPriceMapper::toDTO).
                collect(Collectors.toList()
                );
    }
}
