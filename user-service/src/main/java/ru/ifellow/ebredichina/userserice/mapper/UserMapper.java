package ru.ifellow.ebredichina.userserice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.userserice.model.Customer;
import ru.ifellow.ebredichina.userserice.model.StoreKeeper;
import ru.ifellow.ebredichina.userserice.model.User;
import ru.ifellow.jschool.dto.CreateCustomerDto;
import ru.ifellow.jschool.dto.CreateEmployeeDto;
import ru.ifellow.jschool.dto.GetUserDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {


    @Mapping(target = "password", source = "password")
    @Mapping(target = "userRole", constant = "CUSTOMER")
    @Mapping(target = "name", source = "name")
    public abstract Customer fromCreateDtoCustomerToUser(CreateCustomerDto createCustomerDto);

    public abstract GetUserDto fromUserToGetUserDto(User user);

    public abstract StoreKeeper fromCreateEmployeeDtoToUser(CreateEmployeeDto createEmployeeDto);


}
