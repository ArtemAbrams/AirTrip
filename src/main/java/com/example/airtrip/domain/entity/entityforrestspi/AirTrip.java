package com.example.airtrip.domain.entity.entityforrestspi;

import com.example.airtrip.domain.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trips")
@Builder
public class AirTrip extends BasicEntity {
    private double price;
    private boolean enabled;
    @ManyToOne()
    private Country fromCountry;
    @ManyToOne()
    private Country toCountry;
    @DateTimeFormat(pattern = "dd MMMM yyyy hh:mm:ss")
    private LocalDateTime departureDate;
    @Lob
    @Column(name = "image_company", length = 20971520)
    private byte[] ImageOfAirCompany;
    @ManyToOne
    private Plane plane;
}
