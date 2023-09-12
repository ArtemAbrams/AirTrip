package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.entityforrestspi.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRole(String role);
}
