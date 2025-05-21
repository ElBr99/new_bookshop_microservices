package ru.ifellow.jschool.ebredichina.service;


import ru.ifellow.jschool.dto.GetUserDto;
import ru.ifellow.jschool.ebredichina.jwt.AuthRequest;


public interface AuthenticationService {
    String getToken(AuthRequest authRequest);
    String getAuthenticatedUsername();
    GetUserDto getAuthenticatedUser(String username);
}
