package com.example.airtrip.configuration;

import com.example.airtrip.domain.credential.TelegramCredentials;
import com.example.airtrip.services.implementation.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramConfiguration {
    @Value("${telegram.username}")
    private String telegramUsername;

    @Value("${telegram.token}")
    private String telegramToken;
    @Bean
    public TelegramCredentials telegramCredentials(){
        return new TelegramCredentials(telegramUsername, telegramToken);
    }
    @Bean
    public TelegramBot telegramBot(TelegramCredentials telegramCredentials){
        return new TelegramBot(telegramCredentials, botCommands());
    }
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
        return telegramBotsApi;
    }
    @Bean
    public List<BotCommand> botCommands(){
        var listOfCommands = new ArrayList<BotCommand>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/deletedata", "delete my data"));
        listOfCommands.add(new BotCommand("/help", "get help for bot"));
        listOfCommands.add(new BotCommand("/trips", "get available trips"));
        listOfCommands.add(new BotCommand("/getlink", "link to start work with bot"));
        return listOfCommands;
    }

}
