package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityforrestspi.AirTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirTripRepository extends JpaRepository<AirTrip, Long> {
}
