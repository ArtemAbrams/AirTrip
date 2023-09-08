package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.dto.TelegramTripDto;
import com.example.airtrip.services.MediaService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MediaServiceImpl implements MediaService {
    @Override
    public InputMedia getInputMedia(byte[] image, String filename) {
       var media = new InputMediaPhoto();
       media.setMedia(new InputFile(new ByteArrayInputStream(image), filename + ".png").getNewMediaStream(), filename + ".png");
       return media;
    }
    @Override
    public List<InputMedia> getAllPhotos(TelegramTripDto telegramTripDto) throws NoSuchFieldException {
        return List.of(getInputMedia(telegramTripDto.getPlaneDTO().getPathToImage(), "plane"),
                getInputMedia(telegramTripDto.getCountryDTO().getFile(), "toCountry"));
    }

    @Override
    public List<InputMedia> doCaptionOfMediaMessage(List<InputMedia> inputMedia, String message) {
        int lastIndex = inputMedia.size()-1;
        List<InputMedia> inputMediaList = new ArrayList<>();
        var media = inputMedia.get(lastIndex);
        for (int i=0; i<inputMedia.size()-1; i++){
            inputMediaList.add(inputMedia.get(i));
        }
        media.setCaption(message);
        inputMediaList.add(media);
        return inputMediaList;
    }
}
