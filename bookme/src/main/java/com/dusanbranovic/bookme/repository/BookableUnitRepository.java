package com.dusanbranovic.bookme.repository;

import com.dusanbranovic.bookme.models.BookableUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookableUnitRepository extends JpaRepository<BookableUnit,Long> {
}
