package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityformvc.OrderM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderM, Long> {
}
