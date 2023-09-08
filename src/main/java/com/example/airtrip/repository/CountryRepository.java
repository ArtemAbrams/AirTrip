package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.Country;
import com.example.airtrip.domain.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
