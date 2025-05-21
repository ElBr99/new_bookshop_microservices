package ru.ifellow.jschool.ebredichina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.jschool.dto.CreateCustomerDto;
import ru.ifellow.jschool.ebredichina.jwt.AuthRequest;
import ru.ifellow.jschool.ebredichina.service.impl.AuthenticationServiceImpl;
import ru.ifellow.jschool.ebredichina.service.impl.UserClientServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserClientServiceImpl userClientService;
    private final AuthenticationServiceImpl authenticationService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Validated CreateCustomerDto createCustomerDto) {
        userClientService.addUser(createCustomerDto);
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody @Validated AuthRequest authRequest) {
        return authenticationService.getToken(authRequest);
    }
}
