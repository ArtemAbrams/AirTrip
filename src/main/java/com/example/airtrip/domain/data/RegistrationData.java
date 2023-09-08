package com.example.airtrip.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationData {
    private String name;
    private String lastname;
    private String email;
    private String password;
}
