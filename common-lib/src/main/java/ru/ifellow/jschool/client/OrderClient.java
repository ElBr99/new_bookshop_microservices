package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "book-management-service", url = "${feign.order-service.url}")
public interface OrderClient {
}
