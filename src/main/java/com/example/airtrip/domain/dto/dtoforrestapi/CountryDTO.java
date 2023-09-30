package com.example.airtrip.domain.dto.dtoforrestapi;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryDTO implements Serializable {
    private int id;
    private String name;
    private byte[] file;
}
