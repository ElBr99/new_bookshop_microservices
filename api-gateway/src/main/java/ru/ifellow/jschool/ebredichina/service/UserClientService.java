package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.CreateCustomerDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;

import java.util.UUID;

public interface UserClientService {
    void addUser(CreateCustomerDto createCustomerDto);

    GetUserDto getUser(String username);

    GetUserDto getUserById(UUID userId);
}
