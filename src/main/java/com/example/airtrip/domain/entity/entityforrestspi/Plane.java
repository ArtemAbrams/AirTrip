package com.example.airtrip.domain.entity.entityforrestspi;


import com.example.airtrip.domain.entity.BasicEntity;
import com.example.airtrip.domain.entity.entityforrestspi.AirTrip;
import com.example.airtrip.domain.enums.Colour;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planes")
@Builder
public class Plane extends BasicEntity {
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Colour colour;

    @Column(name = "image_plane", length = 20971520)
    private byte[] imageFile;

    @OneToMany(mappedBy = "plane")
    private List<AirTrip> airTripList;
}
