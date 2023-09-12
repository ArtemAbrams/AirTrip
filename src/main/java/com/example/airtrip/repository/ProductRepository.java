package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityformvc.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
