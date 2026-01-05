package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.BookingResponseDTO;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.User;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.BookingRepository;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public BookingResponseDTO bookAUnit(Long userId, Long unitId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalUser.isEmpty() || optionalUser.isEmpty()) return null;

        /*
        1.Da li postoje user i unit
        2. Da li je datum dobro izabran i validan
        3. Da li ima ima mesta da se bukira?
        4. 

         */

        return null;
    }
}
