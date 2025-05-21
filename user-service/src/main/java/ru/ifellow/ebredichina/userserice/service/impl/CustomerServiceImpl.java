package ru.ifellow.ebredichina.userserice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.ebredichina.userserice.dto.CreateCustomerDto;
import ru.ifellow.ebredichina.userserice.mapper.UserMapper;
import ru.ifellow.ebredichina.userserice.repository.UserRepository;
import ru.ifellow.ebredichina.userserice.service.CustomerService;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public void createCustomer(CreateCustomerDto createCustomerDto) {
        userRepository.save(userMapper.fromCreateDtoCustomerToUser(createCustomerDto));
    }

}
