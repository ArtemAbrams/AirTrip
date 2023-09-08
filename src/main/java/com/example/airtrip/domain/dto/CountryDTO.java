package com.example.airtrip.domain.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryDTO {
    private Long id;
    private String name;
    private byte[] file;
}
