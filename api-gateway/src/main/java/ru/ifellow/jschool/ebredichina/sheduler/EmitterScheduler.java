package ru.ifellow.jschool.ebredichina.sheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import ru.ifellow.jschool.client.FavouriteBookClient;
import ru.ifellow.jschool.dto.EmitterMessage;
import ru.ifellow.jschool.dto.FavouriteBookDto;
import ru.ifellow.jschool.dto.SendBookInfo;
import ru.ifellow.jschool.ebredichina.mapper.FavouriteBookDtoMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmitterScheduler {

    private final Map<UUID, SseEmitter> subscribers;
    private final FavouriteBookClient favouriteBookClient;
    private final FavouriteBookDtoMapper favouriteBookDtoMapper;

    @Scheduled(cron = "0 0 12 * * ?")
    public void schedule() {
        subscribers.forEach((key, sseEmitter) -> {

            List<FavouriteBookDto> favouriteBooks = favouriteBookClient.getFavouriteBooks(key);
            List<SendBookInfo> listForSend = favouriteBookDtoMapper.mapToSendBookInfoList(favouriteBooks);

            EmitterMessage emitterMessage = new EmitterMessage(key.toString(), "Напоминание о книгах, добавленных в избранное", listForSend);

            try {
                sseEmitter.send(emitterMessage);
            } catch (IOException e) {
                sseEmitter.complete();
                subscribers.remove(key);
            }
        });
    }
}
