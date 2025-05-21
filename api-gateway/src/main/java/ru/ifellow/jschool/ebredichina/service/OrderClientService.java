package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import java.util.List;
import java.util.UUID;

public interface OrderClientService {

    OnlineOrderDto registerOrderOnline(CreateOrderDto createOrderDto);

    CreateChequeDto acceptPurchaseOnline(UUID orderId, CreateOrderDto createOrderDto);

    CreateChequeDto doPurchaseOffline(UUID shopId, CreateOrderDto createOrderDto);

    List<OrderDto> findByCustomerId(UUID customerId);

    OrderDto findByOrderId(UUID orderId);

    List<OrderDto> getOrders();

}
