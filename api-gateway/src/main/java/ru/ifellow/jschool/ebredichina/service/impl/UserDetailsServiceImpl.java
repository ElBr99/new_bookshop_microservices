package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.UserClient;
import ru.ifellow.jschool.dto.GetUserDto;


import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GetUserDto userResponse = userClient.getUserByEmail(username);
        return new User(userResponse.getEmail(), userResponse.getPassword(), Collections.singleton(userResponse.getUserRole()));
    }
}
