package ru.ifellow.ebredichina.userserice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.userserice.mapper.UserMapper;
import ru.ifellow.ebredichina.userserice.repository.UserRepository;
import ru.ifellow.ebredichina.userserice.service.UserService;
import ru.ifellow.jschool.dto.GetUserDto;
import ru.ifellow.jschool.exception.UserNotFoundException;
import ru.ifellow.jschool.exception.UsernameNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public GetUserDto getUser(String username) {
        return userRepository.findByEmail(username).map(userMapper::fromUserToGetUserDto)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));
    }

    @Override
    public GetUserDto getUserById(UUID userId) {
        return userRepository.findById(userId).map(userMapper::fromUserToGetUserDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким айди не найден"));
    }

}
