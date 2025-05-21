package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


    @Override
    public OnlineOrderDto registerOrderOnline(CreateOrderDto createOrderDto) {
        return null;
    }

    @Override
    public CreateChequeDto acceptPurchaseOnline(UUID orderId, CreateOrderDto createOrderDto) {
        return null;
    }

    @Override
    public CreateChequeDto doPurchaseOffline(UUID shopId, CreateOrderDto createOrderDto) {
        return null;
    }

    @Override
    public List<OrderDto> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public OrderDto findByOrderId(UUID orderId) {
        return null;
    }

    @Override
    public List<OrderDto> getOrders() {
        return null;
    }
}
