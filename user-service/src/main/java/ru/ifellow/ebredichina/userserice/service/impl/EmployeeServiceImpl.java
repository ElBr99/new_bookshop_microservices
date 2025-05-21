package ru.ifellow.ebredichina.userserice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.userserice.dto.CreateEmployeeDto;
import ru.ifellow.ebredichina.userserice.mapper.UserMapper;
import ru.ifellow.ebredichina.userserice.repository.UserRepository;
import ru.ifellow.ebredichina.userserice.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void createEmployee(CreateEmployeeDto createEmployeeDto) {
        userRepository.save(userMapper.fromCreateEmployeeDtoToUser(createEmployeeDto));
    }
}
