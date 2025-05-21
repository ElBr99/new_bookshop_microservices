package unitTests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.dto.CreateOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.listener.ChequeListener;
import ru.ifellow.jschool.ebredichina.mapper.*;
import ru.ifellow.jschool.ebredichina.model.*;
import ru.ifellow.jschool.ebredichina.repository.OrderRepository;
import ru.ifellow.jschool.ebredichina.service.AuthenticationService;
import ru.ifellow.jschool.ebredichina.service.BookStorageService;
import ru.ifellow.jschool.ebredichina.service.UserClientService;
import ru.ifellow.jschool.ebredichina.service.impl.ChequeServiceImpl;
import ru.ifellow.jschool.ebredichina.service.impl.CommonStorageService;
import ru.ifellow.jschool.ebredichina.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private CommonStorageService<OfflineBookShop> offlineBookShopService;

    @Mock
    private ChequeServiceImpl chequeService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private BookStorageService bookStorageService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private List<ChequeListener> listeners;

    @Mock
    private ToChequeMapperOnline toChequeMapper;

    @Spy
    private ToSoldBooksMapper toSoldBooksMapper = new ToSoldBooksMapperImpl();

    @Spy
    private OrderToOnlineOrderDtoMapper orderToOnlineOrderDtoMapper = new OrderToOnlineOrderDtoMapperImpl();

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private OrderServiceImpl orderService;


    public List<BookStorage> createTestingBookStorages() {
        UUID storageOne = UUID.fromString("35ca5f62-d717-48e1-964f-09711c26f5e1");
        String address1 = "expectedBookStorage1";
        String name1 = "Склад3";
        BookInfo bookInfo1 = BookInfo.builder()
                .id(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"))
                .book(Book.builder()
                        .id(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"))
                        .title("Книга1")
                        .author("Автор1")
                        .genre(BookGenre.STORY)
                        .publishingYear("1985")
                        .build())
                .amount(5)
                .price(BigDecimal.valueOf(300))
                .build();
        BookInfo bookInfo2 = BookInfo.builder()
                .id(UUID.fromString("7b44e8f2-177d-4880-827e-941fa279546d"))
                .book(Book.builder()
                        .id(UUID.fromString("7b44e8f2-177d-4880-827e-941fa279546d"))
                        .title("Книга2")
                        .author("Автор2")
                        .genre(BookGenre.NOVEL)
                        .publishingYear("1986")
                        .build())
                .amount(3)
                .price(BigDecimal.valueOf(400))
                .build();
        Map<UUID, BookInfo> books1 = new HashMap<>();
        books1.put(bookInfo1.getId(), bookInfo1);
        books1.put(bookInfo2.getId(), bookInfo2);

        UUID storageTwo = UUID.fromString("d04bb978-862a-498e-a071-8f09cf713c5c");
        String address2 = "expectedBookStorage2";
        String name2 = "Склад4";

        BookInfo bookInfo3 = BookInfo.builder()
                .id(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"))
                .book(Book.builder()
                        .id(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"))
                        .title("Книга3")
                        .author("Автор3")
                        .genre(BookGenre.STORY)
                        .build())
                .amount(2)
                .price(BigDecimal.valueOf(300))
                .build();

        Map<UUID, BookInfo> books2 = new HashMap<>();
        books2.put(bookInfo1.getId(), bookInfo1);
        books2.put(bookInfo3.getId(), bookInfo2);


        BookStorage bookStorage1 = new BookStorage(storageOne, address1, name1, bookInfo1.getAmount() + bookInfo2.getAmount());
        BookStorage bookStorage2 = new BookStorage(storageTwo, address2, name2, bookInfo1.getAmount() + bookInfo3.getAmount());

        bookStorage1.addBook(bookInfo1);
        bookStorage1.addBook(bookInfo2);
        bookStorage2.addBook(bookInfo1);
        bookStorage2.addBook(bookInfo3);


        return List.of(
                bookStorage1, bookStorage2
        );
    }

    private OfflineBookShop createTestingOfflineBookShops() {
        UUID shopFirst = UUID.fromString("bdf91636-12d3-4ec6-b29e-6d4ae12f8d55");
        String address1 = "expectedBookShop1";
        String name1 = "shop1";

        BookInfo bookInfo1 = BookInfo.builder()
                .id(UUID.fromString("b6beaf2c-83cf-4a08-9d69-4defd07deade"))
                .book(Book.builder()
                        .id(UUID.fromString("b6beaf2c-83cf-4a08-9d69-4defd07deade"))
                        .title("Книга1")
                        .author("Автор1")
                        .genre(BookGenre.STORY)
                        .build())
                .amount(5)
                .price(BigDecimal.valueOf(300))
                .build();

        BookInfo bookInfo2 = BookInfo.builder()
                .id(UUID.fromString("64dac137-e6ec-48a4-abe8-852f66f7c9c9"))
                .book(Book.builder()
                        .id(UUID.fromString("64dac137-e6ec-48a4-abe8-852f66f7c9c9"))
                        .title("Книга2")
                        .author("Автор2")
                        .genre(BookGenre.NOVEL)
                        .build())
                .amount(3)
                .price(BigDecimal.valueOf(400))
                .build();

        Map<UUID, BookInfo> books1 = new HashMap<>();
        books1.put(bookInfo1.getId(), bookInfo1);
        books1.put(bookInfo2.getId(), bookInfo2);

        OfflineBookShop offlineBookShop = new OfflineBookShop(shopFirst, address1, name1, bookInfo1.getAmount() + bookInfo2.getAmount());
        offlineBookShop.addBook(bookInfo1);
        offlineBookShop.addBook(bookInfo2);
        return offlineBookShop;
    }

    @Test
    void registerOrderOnline_OK() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"), "Книга1", 3);
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"), "Книга3", 1);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo, buyBookDto);
        BookStorage expectedStorage = createTestingBookStorages().get(1);


        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440011");
        GetUserDto customer = GetUserDto.builder()
                .email("qwerty@mail.ru")
                .id(customerId)
                .build();
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(BigDecimal.valueOf(1200))
                .build();

        List<SoldBook> soldBooksFromCreateOrder = toSoldBooksMapper.mapToSoldBooks(createOrderDto.getCustomerBox());

        when(bookStorageService.selectsStorageForDelivery(customerList)).thenReturn(expectedStorage);
        when(userClientService.getUserById(customerId)).thenReturn(customer);
        when(authenticationService.getAuthenticatedUsername()).thenReturn(customer.getEmail());
        when(authenticationService.getAuthenticatedUser(customer.getEmail())).thenReturn(customer);

        OnlineOrderDto onlineOrderDto = orderService.registerOrderOnline(createOrderDto);

        verify(orderRepository, times(1)).save(argThat(order -> {
            assertEquals(soldBooksFromCreateOrder.get(0).getBookId(), order.getCustomerBox().get(0).getBookId());
            assertEquals(createOrderDto.getMoneyFromCustomer(), order.getSum());
            return true;
        }));

        assertEquals(customerId, onlineOrderDto.getCustomerId());
        assertEquals(soldBooksFromCreateOrder.get(0).getBookId(), onlineOrderDto.getCustomerBox().get(0).getBookId());
        assertEquals(soldBooksFromCreateOrder.get(1).getBookId(), onlineOrderDto.getCustomerBox().get(1).getBookId());
    }

    @Test
    void acceptPurchaseOnline() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("b6beaf2c-83cf-4a08-9d69-4defd07deade"), "Книга1", 3);
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("006b418d-5b46-45bb-aa8c-6436d4a780f8"), "Книга3", 1);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo, buyBookDto);
        List<SoldBook> soldBooks = toSoldBooksMapper.mapToSoldBooks(customerList);

        when(toSoldBooksMapper.mapToSoldBooks(customerList)).thenReturn(soldBooks);

        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("006b418d-0000-0000-0000-6436d4a780f8"))
                .email("asdfg")
                .build();

        Order expectedOrderForPurchase = Order.builder()
                .id(UUID.fromString("006b418d-0000-0000-0000-6436d4a78444"))
                .customerEmail(customer.getEmail())
                .customer(customer.getId())
                .customerBox(soldBooks)
                .sum(BigDecimal.valueOf(1000))
                .build();

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(expectedOrderForPurchase.getSum())
                .build();
        OffsetDateTime purchaseDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        Cheque chequee = Cheque.builder()
                .order(expectedOrderForPurchase)
                .customerEmail(expectedOrderForPurchase.getCustomerEmail())
                .sum(expectedOrderForPurchase.getSum())
                .chequeType(ChequeType.ONLINE)
                .build();

        CreateChequeDto createChequeDto = CreateChequeDto.builder()
                .orderId(expectedOrderForPurchase.getId())
                .sum(expectedOrderForPurchase.getSum())
                .customerEmail(customer.getEmail())
                .customerBox(toSoldBooksMapper.mapToSoldBooks(createOrderDto.getCustomerBox()))
                .purchaseDate(purchaseDateTime)
                .chequeType(ChequeType.ONLINE)
                .build();

        when(orderRepository.getOrderByParams(customer.getId(), expectedOrderForPurchase.getId(), OrderStatus.PENDING)).thenReturn(Optional.of(expectedOrderForPurchase));
        when(toChequeMapper.mapToCreateChequeDto(chequee)).thenReturn(createChequeDto);
        when(authenticationService.getAuthenticatedUsername()).thenReturn(customer.getEmail());
        when(authenticationService.getAuthenticatedUser(customer.getEmail())).thenReturn(customer);

        CreateChequeDto createChequeDtoForReturn = orderService.acceptPurchaseOnline(expectedOrderForPurchase.getId(), createOrderDto);

        verify(chequeService, times(1)).addCheque(argThat(cheque -> {
            assertEquals(createChequeDtoForReturn.getCustomerEmail(), cheque.getCustomerEmail());
            assertEquals(createChequeDtoForReturn.getOrderId(), cheque.getOrder().getId());
            assertEquals(createChequeDtoForReturn.getChequeType(), cheque.getChequeType());
            return true;
        }));

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(chequeService, times(1)).addCheque(any(Cheque.class));
        verify(listeners, times(1)).forEach(any());

        assertNotNull(createChequeDtoForReturn);
        assertEquals(expectedOrderForPurchase.getSum(), createChequeDtoForReturn.getSum());
        assertEquals(expectedOrderForPurchase.getCustomerBox(), createChequeDtoForReturn.getCustomerBox());
    }


    @Test
    void doPurchaseOffline_OK() {
        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("b6beaf2c-83cf-4a08-9d69-4defd07deade"), "Книга1", 3);
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("64dac137-e6ec-48a4-abe8-852f66f7c9c9"), "Книга2", 1);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo, buyBookDto);
        List<SoldBook> soldBooks = toSoldBooksMapper.mapToSoldBooks(customerList);
        OfflineBookShop offlineBookShop = createTestingOfflineBookShops();

        OffsetDateTime purchaseDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(BigDecimal.valueOf(1300))
                .build();

        Order order = Order.builder()
                .shopId(offlineBookShop.getId())
                .status(OrderStatus.ACCEPTED)
                .sum(createOrderDto.getMoneyFromCustomer())
                .build();

        when(toSoldBooksMapper.mapToSoldBooks(customerList)).thenReturn(soldBooks);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        CreateChequeDto createChequeDto = CreateChequeDto.builder()
                .sum(order.getSum())
                .customerBox(toSoldBooksMapper.mapToSoldBooks(createOrderDto.getCustomerBox()))
                .purchaseDate(purchaseDateTime)
                .purchasePlaceId(offlineBookShop.getId())
                .chequeType(ChequeType.OFFLINE)
                .build();

        Cheque cheque = Cheque.builder()
                .id(UUID.randomUUID())
                .build();

        when(offlineBookShopService.getStorageById(offlineBookShop.getId())).thenReturn(offlineBookShop);
        when(toChequeMapper.mapToCheque(createChequeDto)).thenReturn(cheque);

        CreateChequeDto createChequeDtoo = orderService.doPurchaseOffline(offlineBookShop.getId(), createOrderDto);


        verify(chequeService, times(1)).addCheque(any(Cheque.class));
        verify(listeners, times(1)).forEach(any());

        assertNotNull(createChequeDtoo);
    }


}
