package ru.ifellow.ebredichina.userserice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.userserice.service.CustomerService;
import ru.ifellow.jschool.dto.CreateCustomerDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer (@RequestBody CreateCustomerDto createCustomerDto) {
        customerService.createCustomer(createCustomerDto);
    }
}
