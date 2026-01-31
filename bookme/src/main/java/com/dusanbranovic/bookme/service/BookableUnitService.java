package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.requests.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.responses.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.mappers.BookableUnitMapper;
import com.dusanbranovic.bookme.mappers.PeriodPriceMapper;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.PeriodPrice;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PeriodPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(BookableUnitService.class);

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
        BookableUnit unit = bookableUnitRepository.findById(unitId).orElseThrow(() ->{
            log.error("Unit not found");
            return new EntityNotFoundException("Unit with id " + unitId + " not found");
        });


        PeriodPrice periodPrice = periodPriceMapper.toEntity(periodPriceDTO, unit);

        log.debug("Created periodPrice body {}", periodPrice);

        PeriodPrice savedPeriodPrice = periodPriceRepository.save(periodPrice);

        log.info("Period price created successfully");

        return periodPriceMapper.toDTO(savedPeriodPrice);


    }

    public List<PeriodPriceResponseDTO> getPeriodPrices(Long unitId) {

        BookableUnit unit = bookableUnitRepository.findById(unitId).orElseThrow(() ->{
            log.error("Unit not found");
            return new EntityNotFoundException("Unit with id " + unitId + " not found");
        });

        log.info("Period price fetched successfully");

        return unit.getPeriodPriceList().
                stream().
                map(periodPriceMapper::toDTO).
                collect(Collectors.toList()
                );
    }
}
