package com.example.airtrip.domain.dto.dtoforrestapi;


import com.example.airtrip.domain.enums.Colour;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlaneDTO {
    private String name;
    private Colour colour;
    private byte[] pathToImage;
}
