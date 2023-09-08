package com.example.airtrip.services.implementation;


import com.example.airtrip.domain.credential.TelegramCredentials;
import com.example.airtrip.domain.dto.TelegramTripDto;
import com.example.airtrip.domain.entity.TelegramUser;
import com.example.airtrip.domain.mapper.CountryMapper;
import com.example.airtrip.domain.mapper.PlaneMapper;
import com.example.airtrip.repository.AirTripRepository;
import com.example.airtrip.repository.TelegramUserRepository;
import com.example.airtrip.services.MediaService;
import com.vdurmont.emoji.EmojiParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramCredentials telegramCredentials;
    private final List<BotCommand> commands;
    @Autowired
    private TelegramUserRepository telegramUserRepository;
    @Autowired
    private AirTripRepository airTripRepository;
    @Autowired
    private MediaService mediaService;
    private static final String help = EmojiParser.parseToUnicode(":clipboard:" + " Dieser Bot ist eine Demonstration eines " +
            "Informanten über alle möglichen Fahrten, " +
            "die verfügbar sind. Ich habe keine große Lust," +
            " vorzuschreiben, was das Fahrteam meint, " +
            "also schauen Sie sich einfach deren Beschreibung im Hauptmenü an");
    @PostConstruct
    private void setCommandsToMyBot() {
        try {
            execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        }
        catch (Exception exception){
            log.error("Exception " + exception.getMessage());
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            var text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            var user = update.getMessage().getChat().getUserName();
            switch (text){
                case "/start" :
                    startCommandReceived(chatId, user);
                    saveNewUser(update.getMessage());
                    break;
                case "/help":
                    sendMessage(chatId, help ,user);
                    break;
                case "/getlink":
                    sendMessage(chatId, "t.me/" + telegramCredentials.getTelegramUsername(), user);
                    break;
                case "/trips":
                    sendAllTrips(chatId, user);
                    break;
                default:
                    sendMessage(chatId, "Für Berlin", user);
            }
        }
    }
    public void sendAllTrips(long chatId, String user) {
        var getTrips = airTripRepository.findAll()
                .stream()
                .filter(e -> e.isEnabled() && e.getDepartureDate().isAfter(LocalDateTime.now()))
                .map(e-> {
                    try {
                        return new TelegramTripDto(e.getPrice(), PlaneMapper.entityToDto(e.getPlane()),
                                e.getDepartureDate(), CountryMapper.entityToDto(e.getToCountry()));
                    } catch (IOException ex) {
                        log.error(ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                })
                .toList();
        for (var trip : getTrips) {
            sendTripMessage(trip, user, chatId);
        }
    }
    private void sendTripMessage(TelegramTripDto trip, String user, Long chatId){
        try {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(String.valueOf(chatId));
            var medias = mediaService.getAllPhotos(trip);
            var message = user + getMessageAboutTrip(trip);
            mediaService.doCaptionOfMediaMessage(medias, message);
            sendMediaGroup.setMedias(medias);
            execute(sendMediaGroup);
            log.info("Send message to " + user);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
    private String getMessageAboutTrip(TelegramTripDto trip){
        return ".   Für nur " + trip.getPrice() +" Euro reisen Sie mit einem schönen " + trip.getPlaneDTO().getName() + " Flugzeug nach " + trip.getCountryDTO().getName()+ " . " +
                " Es gibt so viele Sitzplätze im Flugzeug: " + " Tag der Absendung: " + trip.getDepartureDate();
    }
    @Override
    public String getBotUsername() {
        return telegramCredentials.getTelegramUsername();
    }
    @Override
    public String getBotToken() {
        return telegramCredentials.getTelegramToken();
    }
    private void startCommandReceived(long chatId, String username){
        String answer = EmojiParser.parseToUnicode(":de:" +" Hallo " + username + " wir freuen uns, " +
                "dich bei der Luftwaffe begrüßen zu dürfen. " +
                "Bald werden Sie Europa erobern, um Deutschland willen");
        sendMessage(chatId, answer, username);
    }
    private void sendMessage(long chatId, String answer, String username){
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), answer);
        try {
            log.info("Send message to " + username);
            execute(sendMessage);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
    private void saveNewUser(Message message){
        if(telegramUserRepository.findById(message.getChatId()).isEmpty()){
            var telegramUser = TelegramUser.builder()
                    .chatId(message.getChatId())
                    .firstName(message.getChat().getFirstName())
                    .lastName(message.getChat().getLastName())
                    .username(message.getChat().getUserName())
                    .build();
            telegramUserRepository.saveAndFlush(telegramUser);
        }
    }
    public void sendMessageToAllRegisteredUsers(TelegramTripDto telegramTripDto){
        var users = telegramUserRepository.findAll()
                .stream()
                .toList();
        for (var user: users) {
            sendTripMessage(telegramTripDto, user.getUsername(), user.getChatId());
        }
    }
}
