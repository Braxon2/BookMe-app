package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.requests.AddonRequestDTO;
import com.dusanbranovic.bookme.dto.requests.PeriodPriceAddonRequestDTO;
import com.dusanbranovic.bookme.dto.responses.AddonPeriodPriceResponseDTO;
import com.dusanbranovic.bookme.dto.responses.AddonResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityAlreadyExistsExcpetion;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.exceptions.InvalidDateRangeException;
import com.dusanbranovic.bookme.exceptions.InvalidPriceValueException;
import com.dusanbranovic.bookme.models.Addon;
import com.dusanbranovic.bookme.models.BookableUnit;
import com.dusanbranovic.bookme.models.PeriodPriceAddon;
import com.dusanbranovic.bookme.repository.AddonRepository;
import com.dusanbranovic.bookme.repository.BookableUnitRepository;
import com.dusanbranovic.bookme.repository.PeriodPriceAddonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddonService {

    private final AddonRepository addonRepository;
    private final BookableUnitRepository bookableUnitRepository;
    private final PeriodPriceAddonRepository periodPriceAddonRepository;

    public AddonService(AddonRepository addonRepository,
                        BookableUnitRepository bookableUnitRepository,
                        PeriodPriceAddonRepository periodPriceAddonRepository) {
        this.addonRepository = addonRepository;
        this.bookableUnitRepository = bookableUnitRepository;
        this.periodPriceAddonRepository = periodPriceAddonRepository;
    }

    public AddonResponseDTO addAddon(Long unitId, AddonRequestDTO dto) {

        Optional<BookableUnit> optionalBookableUnit = bookableUnitRepository.findById(unitId);

        if(optionalBookableUnit.isEmpty()) {
            throw new EntityNotFoundException("Unit with id " + unitId + " not found");
        }

        BookableUnit unit = optionalBookableUnit.get();

        Optional<Addon> optionalFascillity = addonRepository.findByName(dto.name());

        if(optionalFascillity.isPresent()) throw new EntityAlreadyExistsExcpetion("Addon with " + dto.name() + " already exists");

        Addon addon = new Addon();
        addon.setName(dto.name());
        addon.setPerNight(dto.perNight());
        addon.setBookableUnit(unit);

        addonRepository.save(addon);
        return new AddonResponseDTO(addon.getId(),addon.getName(), addon.isPerNight());
    }

    public List<AddonResponseDTO> getAllAddons() {

        return addonRepository.findAll().
                stream().map(
                        addon ->
                                new AddonResponseDTO(
                                        addon.getId()
                                        ,addon.getName()
                                        , addon.isPerNight()
                                )
                ).collect(Collectors.toList());
    }


    public AddonPeriodPriceResponseDTO addAddonPeriodPrice(Long aid, PeriodPriceAddonRequestDTO dto) {
        Optional<Addon> optionalAddon = addonRepository.findById(aid);

        if(optionalAddon.isEmpty()) {
            throw new EntityNotFoundException("Addon with id " + aid + " not found");
        }

        Addon addon = optionalAddon.get();

        LocalDate start = dto.startDate();
        LocalDate end = dto.endDate();

        if (!start.isBefore(end)) {
            throw new InvalidDateRangeException("Start date must be before end date");
        }

        if(dto.price() <= 0){
            throw new InvalidPriceValueException("Price must be greater than 0");
        }

        PeriodPriceAddon periodPriceAddon = new PeriodPriceAddon(addon,dto.price(),dto.startDate(),dto.endDate());

        addon.getPeriodPriceAddonList().add(periodPriceAddon);

        periodPriceAddonRepository.save(periodPriceAddon);


        return new AddonPeriodPriceResponseDTO(periodPriceAddon.getId(),
                periodPriceAddon.getPrice(),
                periodPriceAddon.getStartDate(),
                periodPriceAddon.getEndDate(),
                new AddonResponseDTO(addon.getId(), addon.getName(), addon.isPerNight())
        );

    }
}
