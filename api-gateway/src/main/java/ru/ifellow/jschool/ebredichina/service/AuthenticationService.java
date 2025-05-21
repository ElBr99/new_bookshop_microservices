package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.jwt.AuthRequest;


public interface AuthenticationService {
    String getToken(AuthRequest authRequest);
    String getAuthenticatedUsername();
    GetUserDto getAuthenticatedUser(String username);
    void setAuthenticatedUser(GetUserDto user);
}
