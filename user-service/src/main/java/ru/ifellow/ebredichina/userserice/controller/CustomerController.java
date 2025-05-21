package ru.ifellow.ebredichina.userserice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.userserice.dto.CreateCustomerDto;
import ru.ifellow.ebredichina.userserice.service.CustomerService;

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
