package ru.ifellow.jschool.ebredichina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.dto.CreateOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OrderDto;
import ru.ifellow.jschool.ebredichina.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderServiceController {

    private final OrderService orderService;
    private static final String CUSTOMER_AUTHORITY = "hasAuthority('CUSTOMER')";
    private static final String STOREKEEPER_AUTHORITY = "hasAuthority('STOREKEEPER')";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(CUSTOMER_AUTHORITY)
    public OnlineOrderDto registerOrderOnline(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.registerOrderOnline(createOrderDto);
    }

    @PostMapping("/online-purchase/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('STOREKEEPER', 'CUSTOMER')")
    public CreateChequeDto acceptPurchase(@PathVariable("orderId") UUID orderId, @RequestBody CreateOrderDto createOrderDto) {
        return orderService.acceptPurchaseOnline(orderId, createOrderDto);
    }

    @PostMapping("/purchase/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('BOOKSELLER')")
    public CreateChequeDto doOfflinePurchase(@PathVariable("shopId") UUID shopId, @RequestBody CreateOrderDto createOrderDto) {
        return orderService.doPurchaseOffline(shopId, createOrderDto);
    }

    @GetMapping
    @PreAuthorize(STOREKEEPER_AUTHORITY)
    public List<OrderDto> viewAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{customerId}")
    @PreAuthorize(CUSTOMER_AUTHORITY)
    public List<OrderDto> viewMyOrders(@PathVariable("customerId") UUID customerId) {
        return orderService.findByCustomerId(customerId);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize(STOREKEEPER_AUTHORITY)
    public OrderDto viewCustomerOrder(@PathVariable("orderId") UUID orderId) {
        return orderService.findByOrderId(orderId);
    }


}
