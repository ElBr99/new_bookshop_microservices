package ru.ifellow.jschool.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.jschool.client.BookStorageClient;
import ru.ifellow.jschool.client.OfflineBookShopClient;
import ru.ifellow.jschool.client.UserClient;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.BuyBookDto;
import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.dto.CreateOrderDto;
import ru.ifellow.jschool.dto.GetUserDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.dto.OrderDto;
import ru.ifellow.jschool.enums.ChequeType;
import ru.ifellow.jschool.enums.OrderStatus;
import ru.ifellow.jschool.exception.BookNotFoundException;
import ru.ifellow.jschool.exception.OrderNotFoundException;
import ru.ifellow.jschool.exception.PaymentFailedException;
import ru.ifellow.jschool.orderservice.listener.ChequeListener;
import ru.ifellow.jschool.orderservice.mapper.OrderMapper;
import ru.ifellow.jschool.orderservice.mapper.OrderToOnlineOrderDtoMapper;
import ru.ifellow.jschool.orderservice.mapper.ToChequeMapperOnline;
import ru.ifellow.jschool.orderservice.mapper.ToSoldBooksMapper;
import ru.ifellow.jschool.orderservice.model.Cheque;
import ru.ifellow.jschool.orderservice.model.Order;
import ru.ifellow.jschool.orderservice.repository.OrderRepository;
import ru.ifellow.jschool.orderservice.service.ChequeService;
import ru.ifellow.jschool.orderservice.service.OrderService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ChequeService chequeService;
    private final OrderRepository orderRepository;
    private final OrderToOnlineOrderDtoMapper orderToOnlineOrderDtoMapper;
    private final List<ChequeListener> listeners;
    private final ToSoldBooksMapper toSoldBooksMapper;
    private final ToChequeMapperOnline toChequeMapper;
    private final OrderMapper orderMapper;
    private final UserClient userClient;
    private final BookStorageClient bookStorageClient;
    private final OfflineBookShopClient offlineBookShopClient;


    @Override
    public OnlineOrderDto registerOrderOnline(UUID userId, CreateOrderDto createOrderDto) {

        BookStorageDto bookStorage = bookStorageClient.selectsStorageForDelivery(createOrderDto.getCustomerBox());
        Map<UUID, BookInfoDto> map = bookStorage.getBooks();
        GetUserDto customer = userClient.getUserById(userId);

        BigDecimal purchaseSum = getPurchaseSum(createOrderDto.getCustomerBox(), map);

        Order order = Order.builder()
                .customerId(customer.getId())
                .customerEmail(customer.getEmail())
                .storageId(bookStorage.getId())
                .customerBox(toSoldBooksMapper.mapToSoldBooks(createOrderDto.getCustomerBox()))
                .status(OrderStatus.PENDING)
                .sum(purchaseSum)
                .build();

        orderRepository.save(order);

        return orderToOnlineOrderDtoMapper.mapToOnlineOrderDto(order);
    }

    @Override
    public CreateChequeDto acceptPurchaseOnline(UUID userId, UUID orderId, CreateOrderDto createOrderDto) {
        GetUserDto user = userClient.getUserById(userId);

        Order orderForPurchase = orderRepository.getOrderByParams(user.getId(), orderId, OrderStatus.PENDING)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с такими параметрами не найден"));

        if (orderForPurchase.getSum().compareTo(createOrderDto.getMoneyFromCustomer()) != 0) {
            throw new PaymentFailedException("Оплата не прошла. Повторите попытку");
        }
        BookStorageDto bookStorage = bookStorageClient.getStorage(orderForPurchase.getStorageId());

        orderForPurchase.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(orderForPurchase);

        Cheque cheque = Cheque.builder()
                .order(orderForPurchase)
                .customerEmail((orderForPurchase.getCustomerEmail()))
                .onlinePurchaseId(bookStorage.getId())
                .sum(orderForPurchase.getSum())
                .purchaseDate(OffsetDateTime.now())
                .chequeType(ChequeType.ONLINE)
                .build();

        chequeService.addCheque(cheque);

        CreateChequeDto createChequeDto = toChequeMapper.mapToCreateChequeDto(cheque);

        listeners.forEach(chequeListener -> chequeListener.onChequeSaved(cheque));
        return createChequeDto;
    }


    @Override
    public CreateChequeDto doPurchaseOffline(UUID userId, UUID shopId, CreateOrderDto createOrderDto) {
        List<BuyBookDto> customerBooks = createOrderDto.getCustomerBox();
        OfflineBookShopDto offlineBookShop = offlineBookShopClient.getShop(shopId);
        Map<UUID, BookInfoDto> map = offlineBookShop.getBooks();

        BigDecimal purchaseSum = getPurchaseSum(customerBooks, map);

        if ((createOrderDto.getMoneyFromCustomer().compareTo(purchaseSum)) != 0) {
            throw new PaymentFailedException("Сбой оплаты");
        }

        Order order = Order.builder()
                .shopId(offlineBookShop.getId())
                .status(OrderStatus.ACCEPTED)
                .sum(purchaseSum)
                .customerBox(toSoldBooksMapper.mapToSoldBooks(customerBooks))
                .build();

        Order savedOrder = orderRepository.save(order);


        CreateChequeDto createChequeDto = CreateChequeDto.builder()
                .orderId(savedOrder.getId())
                .customerBox(toSoldBooksMapper.mapToSoldBooks(createOrderDto.getCustomerBox()))
                .sum(purchaseSum)
                .purchaseDate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .purchasePlaceId(offlineBookShop.getId())
                .chequeType(ChequeType.OFFLINE)
                .build();

        Cheque cheque = toChequeMapper.mapToCheque(createChequeDto);

        chequeService.addCheque(cheque);
        listeners.forEach(chequeListener -> chequeListener.onChequeSaved(cheque));
        return createChequeDto;

    }

    @Override
    public Order getOrder(UUID orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Такой заказ не найден"));
    }

    @Override
    public List<OrderDto> findByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.getOrdersByCustomer(customerId);
        return orderMapper.mapToListOrderDto(orders);
    }

    @Override
    public OrderDto findByOrderId(UUID orderId) {
        return orderMapper.mapToOrderDto(getOrder(orderId));
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderMapper.mapToListOrderDto(orderRepository.findAll());
    }

    private BigDecimal getPurchaseSum(List<BuyBookDto> customerList, Map<UUID, BookInfoDto> map) {
        BigDecimal purchaseSum = BigDecimal.ZERO;
        for (BuyBookDto customerBook : customerList) {
            BookInfoDto bookInfo = map
                    .values()
                    .stream()
                    .filter(bookInfo1 -> bookInfo1.getBook().equals(customerBook.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new BookNotFoundException("Книга не найдена в системе"));

            purchaseSum = purchaseSum.add(getCountBookPrice(customerBook, bookInfo));
        }

        return purchaseSum;

    }

    private static BigDecimal getCountBookPrice(BuyBookDto customerBook, BookInfoDto bookInfo) {
        return bookInfo.getPrice().multiply(BigDecimal.valueOf(customerBook.getAmount()));
    }

}
