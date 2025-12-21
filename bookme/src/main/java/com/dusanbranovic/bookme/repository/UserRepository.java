package com.dusanbranovic.bookme.repository;

import com.dusanbranovic.bookme.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
