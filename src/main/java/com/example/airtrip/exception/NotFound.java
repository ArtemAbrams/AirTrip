package com.example.airtrip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NotFound {
    private String message;
    private LocalDateTime localDateTime;
    private String description;
}
