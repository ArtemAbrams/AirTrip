package com.example.airtrip.domain.data.dataforrestapi;

import com.example.airtrip.domain.enums.Colour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaneData {
    private String name;
    private Colour colour;
}
