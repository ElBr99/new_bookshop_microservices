package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.OrderClient;
import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import ru.ifellow.jschool.ebredichina.service.OrderClientService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClient orderClient;


    @Override
    public OnlineOrderDto registerOrderOnline(CreateOrderDto createOrderDto) {
        return orderClient.registerOrderOnline(createOrderDto);
    }

    @Override
    public CreateChequeDto acceptPurchaseOnline(UUID orderId, CreateOrderDto createOrderDto) {
        return orderClient.acceptPurchase(orderId, createOrderDto);
    }

    @Override
    public CreateChequeDto doPurchaseOffline(UUID shopId, CreateOrderDto createOrderDto) {
        return orderClient.doOfflinePurchase(shopId, createOrderDto);
    }

    @Override
    public List<OrderDto> findByCustomerId(UUID customerId) {
        return orderClient.viewMyOrders(customerId);
    }

    @Override
    public OrderDto findByOrderId(UUID orderId) {
        return orderClient.viewCustomerOrder(orderId);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderClient.viewAllOrders();
    }
}
