package ru.ifellow.ebredichina.userserice.service;

import ru.ifellow.ebredichina.userserice.dto.GetUserDto;

import java.util.UUID;

public interface UserService {

    GetUserDto getUser(String username);

    GetUserDto getUserById(UUID userId);
}
