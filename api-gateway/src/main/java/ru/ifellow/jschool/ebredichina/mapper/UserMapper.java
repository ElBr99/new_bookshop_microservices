package ru.ifellow.jschool.ebredichina.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifellow.jschool.ebredichina.dto.CreateCustomerDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Lazy
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(createCustomerDto.getPassword()))")
    @Mapping(target = "userRole", constant = "CUSTOMER")
    public abstract GetUserDto fromCreateDtoToUser(CreateCustomerDto createCustomerDto);


}
