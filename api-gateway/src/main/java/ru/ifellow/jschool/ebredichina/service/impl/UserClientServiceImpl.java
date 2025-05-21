package ru.ifellow.jschool.ebredichina.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.UserClient;
import ru.ifellow.jschool.dto.CreateCustomerDto;
import ru.ifellow.jschool.dto.GetUserDto;
import ru.ifellow.jschool.ebredichina.service.UserClientService;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserClientServiceImpl implements UserClientService {

    private final UserClient userClient;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    public void addUser(CreateCustomerDto createCustomerDto) {

        createCustomerDto.setPassword(passwordEncoder.encode(createCustomerDto.getPassword()));
        userClient.createCustomer(createCustomerDto);

    }

    @Override
    public GetUserDto getUser(String username) {
        return userClient.getUserByEmail(username);

    }

    @Override
    public GetUserDto getUserById(UUID userId) {
        return userClient.getUserById(userId);

    }

}
