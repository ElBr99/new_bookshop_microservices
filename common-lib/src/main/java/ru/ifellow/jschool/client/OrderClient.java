package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "book-management-service", url = "${feign.order-service.url}")
public interface OrderClient {

    @PostMapping("/api/v1/orders")
    @ResponseStatus(HttpStatus.CREATED)
    OnlineOrderDto registerOrderOnline(@RequestBody CreateOrderDto createOrderDto);

    @PostMapping("/api/v1/orders/online-purchase/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    CreateChequeDto acceptPurchase(@PathVariable("orderId") UUID orderId, @RequestBody CreateOrderDto createOrderDto);

    @PostMapping("/api/v1/orders/purchase/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    CreateChequeDto doOfflinePurchase(@PathVariable("shopId") UUID shopId, @RequestBody CreateOrderDto createOrderDto);

    @GetMapping("/api/v1/orders")
    List<OrderDto> viewAllOrders();

    @GetMapping("/api/v1/orders/{customerId}")
    List<OrderDto> viewMyOrders(@PathVariable("customerId") UUID customerId);

    @GetMapping("/api/v1/orders/{orderId}")
    OrderDto viewCustomerOrder(@PathVariable("orderId") UUID orderId);
}