package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.responses.UserDTO;
import com.dusanbranovic.bookme.mappers.UserMapper;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().
                stream().
                map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

}
