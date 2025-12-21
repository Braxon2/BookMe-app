package com.dusanbranovic.bookme.service;

import com.dusanbranovic.bookme.dto.UserDTO;
import com.dusanbranovic.bookme.models.User;
import com.dusanbranovic.bookme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(UserDTO dto) {
        User user = new User();
        user.setUserType(dto.userType());
        user.setPhoneNumber(dto.phoneNumber());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        return userRepository.save(user);
    }
}
