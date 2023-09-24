package com.example.airtrip.domain.dto.dtoforrestapi;


import com.example.airtrip.domain.enums.Colour;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlaneDTO implements Serializable{
    private String name;
    private Colour colour;
    private byte[] pathToImage;
}
