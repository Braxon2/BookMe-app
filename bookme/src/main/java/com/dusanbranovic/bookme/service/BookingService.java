package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.requests.AddonsRequestDTO;
import com.dusanbranovic.bookme.dto.responses.AddonResponseDTO;
import com.dusanbranovic.bookme.dto.responses.BookableUnitsResponseDTO;
import com.dusanbranovic.bookme.dto.requests.BookingRequestDTO;
import com.dusanbranovic.bookme.dto.responses.BookingResponseDTO;
import com.dusanbranovic.bookme.dto.responses.UserDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.exceptions.UnitAlreadyBookedException;
import com.dusanbranovic.bookme.models.*;
import com.dusanbranovic.bookme.repository.AddonRepository;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.BookingRepository;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookableUnitRepository bookableUnitRepository;
    private final AddonRepository addonRepository;

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);


    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          BookableUnitRepository bookableUnitRepository, AddonRepository addonRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bookableUnitRepository = bookableUnitRepository;
        this.addonRepository = addonRepository;
    }

    public BookingResponseDTO bookAUnit(Long unitId, BookingRequestDTO bookingRequestDTO) {
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalBookableUnit.isEmpty()) {
            log.error("Unit not found");
            throw new EntityNotFoundException("Unit with id " + unitId + " not found");
        }

        List<Long> addonIds = bookingRequestDTO.addons().stream().map(AddonsRequestDTO::id).toList();

        List<Addon> addons = new ArrayList<>();
        for(int i = 0; i < addonIds.size(); i++){
            Optional<Addon> optionalAddon = addonRepository.findById(addonIds.get(i));

            if(optionalAddon.isEmpty()) {
                log.error("Addon not found");
                throw new EntityNotFoundException("Addon with id " + addonIds.get(i) + " not found");
            }
            if(optionalAddon.get().getBookableUnit().getId() != unitId) {
                log.error("Addon not found in unit " + unitId);
                throw new IllegalArgumentException("Addon with id " + addonIds.get(i) + " not found in unit");
            }
            addons.add(optionalAddon.get());
        }



        BookableUnit unit = optionalBookableUnit.get();

        LocalDate start = bookingRequestDTO.start_date();
        LocalDate end = bookingRequestDTO.end_date();

        if (!start.isBefore(end)) {
            log.error("Start date must be before end date");
            throw new IllegalArgumentException("Start date must be before end date");
        }

        LocalDateTime checkIn = start.atStartOfDay();
        LocalDateTime checkOut = end.atStartOfDay();

        Long overlappingCount  =
                bookingRepository.countOverlappingBookings(unitId, checkIn, checkOut);

        if (overlappingCount >= unit.getTotalUnits()) {
            log.error("No available units for selected dates");
            throw new IllegalStateException("No available units for selected dates");
        }

        List<PeriodPrice> prices = unit.getPeriodPriceList();
        double totalAddonPrice = 0;
        double totalPrice = calculatePrice(start,end,prices);

        for(int i = 0; i < addons.size(); i++){
            totalAddonPrice += calculateAddonPrice(start,end,addons.get(i));
        }

        totalPrice += totalAddonPrice;

        log.info("Total price calculated successfully");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User guest = (User) auth.getPrincipal();

        Booking booking = new Booking(
                unit,
                guest,
                totalPrice,
                LocalDate.now(),
                checkIn,
                checkOut,
                BookingStatus.CONFIRMED
        );

        log.info("Booking created successfully");

        bookingRepository.save(booking);


        BookableUnitsResponseDTO unitDTO = new BookableUnitsResponseDTO(
                unit.getId(),
                unit.getMaxCapacity(),
                unit.getSquareMeters(),
                unit.getTotalUnits(),
                unit.getSingleBeds(),
                unit.getDoubleBeds(),
                unit.getMaxAdultCapacity(),
                unit.getMaxKidsCapacity(),
                unit.getName()
        );

        UserDTO guestDTO = new UserDTO(
                guest.getId(),
                guest.getRole(),
                guest.getEmail(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getPhoneNumber()
        );

        return new BookingResponseDTO(
                unitDTO,
                guestDTO,
                totalPrice,
                LocalDate.now(),
                checkIn,
                checkOut,
                BookingStatus.CONFIRMED
        );



    }

    private double calculatePrice(
            LocalDate start,
            LocalDate end,
            List<PeriodPrice> prices
    ){

        double totalPrice = 0.0;
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

            LocalDate finalDate = date;
            PeriodPrice priceForDay = prices.stream()
                    .filter(p ->
                            !finalDate.isBefore(p.getStartDate()) &&
                                    !finalDate.isAfter(p.getEndDate())
                    )
                    .findFirst()
                    .orElseThrow(() -> {
                        log.warn("No price defined for date " + finalDate);
                        return new IllegalStateException(
                                "No price defined for date " + finalDate);
                    });


            totalPrice += priceForDay.getPricePerNight();
        }

        return totalPrice;
    }

    private double calculateAddonPrice(
            LocalDate start,
            LocalDate end,
            Addon addon
    ) {
        List<PeriodPriceAddon> prices = addon.getPeriodPriceAddonList();

        if (addon.isPerNight()) {
            return calculatePerNight(start, end, prices);
        } else {
            return calculateOnce(start, prices);
        }
    }

    private double calculateOnce(
            LocalDate start,
            List<PeriodPriceAddon> prices
    ) {
        PeriodPriceAddon price = prices.stream()
                .filter(p ->
                        !start.isBefore(p.getStartDate()) &&
                                !start.isAfter(p.getEndDate())
                )
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("No addon price defined");
                    return new IllegalStateException("No addon price defined");
                });

        return price.getPrice();
    }

    private double calculatePerNight(
            LocalDate start,
            LocalDate end,
            List<PeriodPriceAddon> prices
    ){

        double totalPrice = 0.0;
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

            LocalDate finalDate = date;
            PeriodPriceAddon priceForDay = prices.stream()
                    .filter(p ->
                            !finalDate.isBefore(p.getStartDate()) &&
                                    !finalDate.isAfter(p.getEndDate())
                    )
                    .findFirst()
                    .orElseThrow(() -> {
                        log.warn("No price defined for date " + finalDate);
                                return new IllegalStateException(
                                        "No price defined for date " + finalDate);
                            }
                    );

            totalPrice += priceForDay.getPrice();
        }

        return totalPrice;
    }
}
