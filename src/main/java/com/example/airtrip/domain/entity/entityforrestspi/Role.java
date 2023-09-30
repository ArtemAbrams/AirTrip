package com.example.airtrip.domain.entity.entityforrestspi;


import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @Override
    public String getAuthority() {
        return role;
    }
}
