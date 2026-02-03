package com.dusanbranovic.bookme.repository;

import com.dusanbranovic.bookme.models.BookableUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookableUnitRepository extends JpaRepository<BookableUnit,Long> {

    @Query("""
    SELECT bu
    FROM BookableUnit bu
    JOIN bu.property p
    WHERE LOWER(p.city) = LOWER(:city)
    AND LOWER(p.country) = LOWER(:country)
    AND (:adults + :kids) <= bu.maxCapacity
    AND :adults <= bu.maxAdultCapacity
    AND :kids <= bu.maxKidsCapacity
    AND bu.totalUnits > (
        SELECT COUNT(b) FROM Booking b
        WHERE b.bookableUnit.id = bu.id
        AND b.status <> 'CANCELLED'
        AND :start < b.checkOut
        AND :end > b.checkIn
    )
    """)
    List<BookableUnit> searchUnitsByCriteria(
            @Param("city") String city,
            @Param("country") String country,
            @Param("adults") int adults,
            @Param("kids") int kids,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
