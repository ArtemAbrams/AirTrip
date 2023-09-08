package com.example.airtrip.domain.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TelegramCredentials {
    private String telegramUsername;
    private String telegramToken;
}
