package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
