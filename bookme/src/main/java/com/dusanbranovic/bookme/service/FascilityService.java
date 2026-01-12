package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.FascilityRequestDTO;
import com.dusanbranovic.bookme.dto.FascilityResponseDTO;
import com.dusanbranovic.bookme.exceptions.EntityAlreadyExistsExcpetion;
import com.dusanbranovic.bookme.exceptions.EntityNotFoundException;
import com.dusanbranovic.bookme.models.Fascillity;
import com.dusanbranovic.bookme.repository.ContactPersonRepository;
import com.dusanbranovic.bookme.repository.FasiliityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FascilityService {

    private final FasiliityRepository fasiliityRepository;

    public FascilityService(FasiliityRepository fasiliityRepository) {
        this.fasiliityRepository = fasiliityRepository;
    }

    public FascilityResponseDTO addFascility(FascilityRequestDTO dto) {

        Optional<Fascillity> optionalFascillity = fasiliityRepository.findByName(dto.name());

        if(optionalFascillity.isPresent()) throw new EntityAlreadyExistsExcpetion("Fascility already exists");

        Fascillity fascillity = new Fascillity();
        fascillity.setName(dto.name());

        fasiliityRepository.save(fascillity);
        return new FascilityResponseDTO(fascillity.getId(),fascillity.getName());
    }
}
