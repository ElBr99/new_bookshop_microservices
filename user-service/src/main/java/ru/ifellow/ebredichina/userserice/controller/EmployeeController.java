package ru.ifellow.ebredichina.userserice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.userserice.dto.CreateEmployeeDto;
import ru.ifellow.ebredichina.userserice.service.EmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody CreateEmployeeDto createEmployeeDto) {
        employeeService.createEmployee(createEmployeeDto);
    }

}
