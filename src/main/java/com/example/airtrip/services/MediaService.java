package com.example.airtrip.services;

import com.example.airtrip.domain.dto.AirTripDTO;
import com.example.airtrip.domain.dto.TelegramTripDto;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

import java.util.List;

public interface MediaService {
    InputMedia getInputMedia(byte[] image, String filename);

    List<InputMedia> getAllPhotos(TelegramTripDto telegramTripDto) throws NoSuchFieldException;

    List<InputMedia> doCaptionOfMediaMessage(List<InputMedia> inputMedia, String message);

}
