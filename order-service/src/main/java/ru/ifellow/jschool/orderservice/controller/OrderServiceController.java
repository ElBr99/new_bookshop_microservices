package ru.ifellow.jschool.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import ru.ifellow.jschool.orderservice.service.OrderService;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderServiceController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OnlineOrderDto registerOrderOnline(@RequestParam("userId") UUID userId, @RequestBody CreateOrderDto createOrderDto) {
        return orderService.registerOrderOnline(userId, createOrderDto);
    }

    @PostMapping("/online-purchase/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public CreateChequeDto acceptPurchase(@PathVariable("orderId") UUID orderId,
                                          @RequestParam("userId") UUID userId,
                                          @RequestBody CreateOrderDto createOrderDto) {
        return orderService.acceptPurchaseOnline(userId, orderId, createOrderDto);
    }

    @PostMapping("/purchase/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    public CreateChequeDto doOfflinePurchase(@RequestParam("userId") UUID userId,
                                             @PathVariable("shopId") UUID shopId,
                                             @RequestBody CreateOrderDto createOrderDto) {
        return orderService.doPurchaseOffline(userId, shopId, createOrderDto);
    }

    @GetMapping
    public List<OrderDto> viewAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{customerId}")
    public List<OrderDto> viewMyOrders(@PathVariable("customerId") UUID customerId) {
        return orderService.findByCustomerId(customerId);
    }

    @GetMapping("/{orderId}")
    public OrderDto viewCustomerOrder(@PathVariable("orderId") UUID orderId) {
        return orderService.findByOrderId(orderId);
    }


}
