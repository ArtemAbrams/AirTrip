package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConflictCountryRepository extends JpaRepository<ConflictCountry, Long> {
}
