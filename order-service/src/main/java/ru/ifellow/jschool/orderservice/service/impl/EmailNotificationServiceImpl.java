package ru.ifellow.jschool.orderservice.service.impl;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl {

    public void sendEmail(String recipient, String subject, String body) {
        System.out.println("=== Отправка письма ===");
        System.out.println("Получатель: " + recipient);
        System.out.println("Тема: " + subject);
        System.out.println("Текст сообщения: " + body);
        System.out.println("Письмо успешно отправлено!");
    }
}


