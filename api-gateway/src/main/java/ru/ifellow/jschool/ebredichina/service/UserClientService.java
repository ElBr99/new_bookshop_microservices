package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.dto.CreateCustomerDto;
import ru.ifellow.jschool.dto.GetUserDto;
import java.util.UUID;

public interface UserClientService {
    void addUser(CreateCustomerDto createCustomerDto);

    GetUserDto getUser(String username);

    GetUserDto getUserById(UUID userId);
}
