package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.BookableUnitsResponseDTO;
import com.dusanbranovic.bookme.dto.BookingRequestDTO;
import com.dusanbranovic.bookme.dto.BookingResponseDTO;
import com.dusanbranovic.bookme.dto.UserDTO;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.exceptions.UnitAlreadyBookedException;
import com.dusanbranovic.bookme.models.*;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.BookingRepository;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookableUnitRepository bookableUnitRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, BookableUnitRepository bookableUnitRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bookableUnitRepository = bookableUnitRepository;
    }

    public BookingResponseDTO bookAUnit(Long unitId, BookingRequestDTO bookingRequestDTO) {
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalBookableUnit.isEmpty()) {
            throw new EntityNotFoundException("Unit with id " + unitId + " not found");
        }

        BookableUnit unit = optionalBookableUnit.get();

        LocalDate start = bookingRequestDTO.start_date();
        LocalDate end = bookingRequestDTO.end_date();

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        LocalDateTime checkIn = start.atStartOfDay();
        LocalDateTime checkOut = end.atStartOfDay();

        List<Booking> overlaps =
                bookingRepository.findOverlappingBookings(unitId, checkIn, checkOut);

        if (!overlaps.isEmpty()) {
            throw new UnitAlreadyBookedException("Unit is not available for selected dates");
        }

        List<PeriodPrice> prices = unit.getPeriodPriceList();

        double totalPrice = 0.0;

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

            LocalDate finalDate = date;
            PeriodPrice priceForDay = prices.stream()
                    .filter(p ->
                            !finalDate.isBefore(p.getStartDate()) &&
                                    !finalDate.isAfter(p.getEndDate())
                    )
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "No price defined for date " + finalDate));

            totalPrice += priceForDay.getPricePerNight();
        }

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
}
