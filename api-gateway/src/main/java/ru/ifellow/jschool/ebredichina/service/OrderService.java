package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.dto.CreateOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OrderDto;
import ru.ifellow.jschool.ebredichina.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OnlineOrderDto registerOrderOnline(CreateOrderDto createOrderDto);

    CreateChequeDto acceptPurchaseOnline(UUID orderId, CreateOrderDto createOrderDto);

    CreateChequeDto doPurchaseOffline(UUID shopId, CreateOrderDto createOrderDto);

    Order getOrder(UUID orderId);

    List<OrderDto> findByCustomerId(UUID customerId);

    OrderDto findByOrderId(UUID orderId);

    List<OrderDto> getOrders();

}
