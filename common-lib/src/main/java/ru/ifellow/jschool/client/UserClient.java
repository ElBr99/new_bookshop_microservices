package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifellow.jschool.dto.CreateCustomerDto;
import ru.ifellow.jschool.dto.GetUserDto;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${feign.user-service.url}")
public interface UserClient {

    @GetMapping("api/v1/users")
    GetUserDto getUserByEmail(@RequestParam("username") String username);

    @GetMapping("api/v1/users/ids")
    GetUserDto getUserById(@RequestParam("userId") UUID userId);

    @PostMapping("api/v1/customers")
    void createCustomer(@RequestBody CreateCustomerDto createCustomerDtoEncoded);
}
