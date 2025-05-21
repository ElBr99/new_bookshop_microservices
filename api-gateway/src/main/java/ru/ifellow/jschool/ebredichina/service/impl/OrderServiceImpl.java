package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.jschool.ebredichina.dto.*;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.exception.BookNotFoundException;
import ru.ifellow.jschool.ebredichina.exception.OrderNotFoundException;
import ru.ifellow.jschool.ebredichina.exception.PaymentFailedException;
import ru.ifellow.jschool.ebredichina.listener.ChequeListener;
import ru.ifellow.jschool.ebredichina.mapper.OrderMapper;
import ru.ifellow.jschool.ebredichina.mapper.OrderToOnlineOrderDtoMapper;
import ru.ifellow.jschool.ebredichina.mapper.ToChequeMapperOnline;
import ru.ifellow.jschool.ebredichina.mapper.ToSoldBooksMapper;
import ru.ifellow.jschool.ebredichina.model.*;
import ru.ifellow.jschool.ebredichina.repository.OrderRepository;
import ru.ifellow.jschool.ebredichina.service.*;

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

    private final CommonStorageService<OfflineBookShop> offlineBookShopService;
    private final ChequeService chequeService;
    private final BookStorageService bookStorageService;
    private final CommonStorageService<BookStorage> bookStorageCommonStorageService;
    private final OrderRepository orderRepository;
    private final OrderToOnlineOrderDtoMapper orderToOnlineOrderDtoMapper;
    private final List<ChequeListener> listeners;
   // private final CustomerService customerService;
    private final ToSoldBooksMapper toSoldBooksMapper;
    private final ToChequeMapperOnline toChequeMapper;
    private final OrderMapper orderMapper;
    private final AuthenticationService authenticationService;
    private final UserClientService userClientService;


    @Override
    public OnlineOrderDto registerOrderOnline(CreateOrderDto createOrderDto) {

        BookStorage bookStorage = bookStorageService.selectsStorageForDelivery(createOrderDto.getCustomerBox());
        Map<UUID, BookInfo> map = bookStorage.getBooks();
        String username = authenticationService.getAuthenticatedUsername();
        GetUserDto user = authenticationService.getAuthenticatedUser(username);
        GetUserDto customer = userClientService.getUserById(user.getId());

        BigDecimal purchaseSum = getPurchaseSum(createOrderDto.getCustomerBox(), map);

        Order order = Order.builder()
                .customer(customer.getId())
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
    public CreateChequeDto acceptPurchaseOnline(UUID orderId, CreateOrderDto createOrderDto) {

        String username = authenticationService.getAuthenticatedUsername();
        GetUserDto user = authenticationService.getAuthenticatedUser(username);

        Order orderForPurchase = orderRepository.getOrderByParams(user.getId(), orderId, OrderStatus.PENDING)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с такими параметрами не найден"));

        if (orderForPurchase.getSum().compareTo(createOrderDto.getMoneyFromCustomer()) != 0) {
            throw new PaymentFailedException("Оплата не прошла. Повторите попытку");
        }
        BookStorage bookStorage = bookStorageCommonStorageService.getStorageById(orderForPurchase.getStorageId());

        orderForPurchase.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(orderForPurchase);

        Cheque cheque = Cheque.builder()
                .order(orderForPurchase)
                .customerEmail((orderForPurchase.getCustomerEmail()))
                .onlineShopPurchase(bookStorage)
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
    public CreateChequeDto doPurchaseOffline(UUID shopId, CreateOrderDto createOrderDto) {
        List<BuyBookDto> customerBooks = createOrderDto.getCustomerBox();
        OfflineBookShop offlineBookShop = offlineBookShopService.getStorageById(shopId);
        Map<UUID, BookInfo> map = offlineBookShop.getBooks();

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

    private BigDecimal getPurchaseSum(List<BuyBookDto> customerList, Map<UUID, BookInfo> map) {
        BigDecimal purchaseSum = BigDecimal.ZERO;
        for (BuyBookDto customerBook : customerList) {
            BookInfo bookInfo = map
                    .values()
                    .stream()
                    .filter(bookInfo1 -> bookInfo1.getBook().getId().equals(customerBook.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new BookNotFoundException("Книга не найдена в системе"));

            purchaseSum = purchaseSum.add(bookInfo.countBookPrice(bookInfo.getPrice(), customerBook.getAmount()));
        }

        return purchaseSum;

    }
}
