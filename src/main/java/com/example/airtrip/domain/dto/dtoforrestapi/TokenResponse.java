package com.example.airtrip.domain.dto.dtoforrestapi;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String token;
    private List<String> roleDTOList;
}
