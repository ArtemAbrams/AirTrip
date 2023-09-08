package com.example.airtrip.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country extends BasicEntity {
    private String name;
    @Lob
    @Column(name = "flag_country", length = 20971520)
    private byte[] imageFile;
    @OneToMany(mappedBy = "fromCountry")
    private List<AirTrip> initialRoutes;
    @OneToMany(mappedBy = "toCountry")
    private List<AirTrip> finalRoutes;
}
