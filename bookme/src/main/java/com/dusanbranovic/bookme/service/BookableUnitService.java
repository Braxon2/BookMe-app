package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.requests.PeriodPriceRequestDTO;
import com.dusanbranovic.bookme.dto.responses.BookableUnitCardDTO;
import com.dusanbranovic.bookme.dto.responses.PeriodPriceResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.mappers.BookableUnitMapper;
import com.dusanbranovic.bookme.mappers.PeriodPriceMapper;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.PeriodPrice;
import com.dusanbranovic.bookme.models.PropertyImage;
import com.dusanbranovic.bookme.models.UnitImage;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PeriodPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Transactional(readOnly = true)
    public List<BookableUnitCardDTO> searchUnits(
            String city,
            String country,
            int adults,
            int kids,
            LocalDate startDate,
            LocalDate endDate
    ) {
        LocalDateTime checkIn = startDate.atStartOfDay();
        LocalDateTime checkOut = endDate.atStartOfDay();


        List<BookableUnit> availableUnits = bookableUnitRepository.searchUnitsByCriteria(
                city, country, adults, kids, checkIn, checkOut
        );

        List<BookableUnitCardDTO> resultCards = new ArrayList<>();


        for (BookableUnit unit : availableUnits) {
            try {
                double totalPrice = calculatePriceForDates(startDate, endDate, unit.getPeriodPriceList());


                String imageUrl = null;
                List<PropertyImage> propertyImages = unit.getProperty().getImages();

                if (propertyImages != null && !propertyImages.isEmpty()) {
                    imageUrl = propertyImages.stream()
                            .filter(img -> Boolean.TRUE.equals(img.getPrimary()))
                            .map(PropertyImage::getUrl)
                            .findFirst()
                            .orElse(
                                    propertyImages.get(0).getUrl()
                            );
                }

                resultCards.add(new BookableUnitCardDTO(
                        unit.getId(),
                        unit.getProperty().getName(),
                        unit.getName(),
                        unit.getProperty().getAddress(),
                        unit.getProperty().getCity(),
                        unit.getProperty().getCountry(),
                        imageUrl,
                        unit.getSingleBeds(),
                        unit.getDoubleBeds(),
                        totalPrice
                ));

            } catch (Exception e) {
                log.warn("Skipping unit {} from search due to pricing error: {}", unit.getId(), e.getMessage());
            }
        }

        return resultCards;
    }

    private double calculatePriceForDates(LocalDate start, LocalDate end, List<PeriodPrice> prices) {
        double totalPrice = 0.0;

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            LocalDate finalDate = date;

            PeriodPrice priceForDay = prices.stream()
                    .filter(p -> !finalDate.isBefore(p.getStartDate()) && !finalDate.isAfter(p.getEndDate()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No price defined for date " + finalDate));

            totalPrice += priceForDay.getPricePerNight();
        }
        return totalPrice;
    }
}
