package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.service.AuthenticationService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SseEmitterService {

    private final Map<UUID, SseEmitter> subscribers;
    private final AuthenticationService authenticationService;


    public SseEmitter createSseEmitter() {
        SseEmitter sseEmitter = new SseEmitter(-1L);
        String username = authenticationService.getAuthenticatedUsername();
        GetUserDto getUserDto = authenticationService.getAuthenticatedUser(username);
        subscribers.put(getUserDto.getId(), sseEmitter);

        sseEmitter.onTimeout(() -> subscribers.remove(getUserDto.getId(), sseEmitter));
        sseEmitter.onError(throwable -> subscribers.remove(getUserDto.getId(), sseEmitter));

        return sseEmitter;
    }

}
