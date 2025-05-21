package ru.ifellow.ebredichina.userserice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.userserice.dto.CreateCustomerDto;
import ru.ifellow.ebredichina.userserice.dto.CreateEmployeeDto;
import ru.ifellow.ebredichina.userserice.dto.GetUserDto;
import ru.ifellow.ebredichina.userserice.model.Customer;
import ru.ifellow.ebredichina.userserice.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {


    @Mapping(target = "password", source = "password")
    @Mapping(target = "userRole", constant = "CUSTOMER")
    @Mapping(target = "name", source = "name")
    public abstract Customer fromCreateDtoCustomerToUser(CreateCustomerDto createCustomerDto);

    public abstract GetUserDto fromUserToGetUserDto (User user);

    public abstract User fromCreateEmployeeDtoToUser (CreateEmployeeDto createEmployeeDto);


}
