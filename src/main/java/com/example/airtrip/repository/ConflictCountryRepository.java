package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConflictCountryRepository extends JpaRepository<ConflictCountry, Long> {
    List<ConflictCountry> findConflictCountriesByNameLike(String term);
}
