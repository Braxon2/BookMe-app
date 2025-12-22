package com.dusanbranovic.bookme.repository;


 import com.dusanbranovic.bookme.models.Fascillity;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FasiliityRepository extends JpaRepository<Fascillity, Long> {
}
