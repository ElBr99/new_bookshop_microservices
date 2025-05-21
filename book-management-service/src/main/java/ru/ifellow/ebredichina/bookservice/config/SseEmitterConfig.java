package ru.ifellow.ebredichina.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SseEmitterConfig {

    @Bean
    public Map<UUID, SseEmitter> createEmitterMap() {
        return new ConcurrentHashMap<>();
    }
}
