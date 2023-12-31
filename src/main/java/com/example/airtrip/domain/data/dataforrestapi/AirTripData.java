package com.example.airtrip.domain.data.dataforrestapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirTripData {
    private double price;
    private boolean enabled;
    private Long fromCountryId;
    private Long toCountryId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDate;
    private Long planeId;
}
