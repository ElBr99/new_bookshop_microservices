package ru.ifellow.ebredichina.userserice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.ebredichina.userserice.mapper.UserMapper;
import ru.ifellow.ebredichina.userserice.repository.UserRepository;
import ru.ifellow.ebredichina.userserice.service.CustomerService;
import ru.ifellow.jschool.dto.CreateCustomerDto;

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
