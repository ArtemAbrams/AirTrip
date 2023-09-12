package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityforrestspi.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
