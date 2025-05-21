package ru.ifellow.jschool.orderservice.service;

import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import ru.ifellow.jschool.orderservice.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OnlineOrderDto registerOrderOnline(UUID userId, CreateOrderDto createOrderDto);

    CreateChequeDto acceptPurchaseOnline(UUID userId,UUID orderId, CreateOrderDto createOrderDto);

    CreateChequeDto doPurchaseOffline(UUID userId, UUID shopId, CreateOrderDto createOrderDto);

    Order getOrder(UUID orderId);

    List<OrderDto> findByCustomerId(UUID customerId);

    OrderDto findByOrderId(UUID orderId);

    List<OrderDto> getOrders();

}
