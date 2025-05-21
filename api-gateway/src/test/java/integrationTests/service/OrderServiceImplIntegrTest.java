package integrationTests.service;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.dto.CreateOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.enums.UserRole;
import ru.ifellow.jschool.ebredichina.model.*;
import ru.ifellow.jschool.ebredichina.repository.*;
import ru.ifellow.jschool.ebredichina.service.UserClientService;
import ru.ifellow.jschool.ebredichina.service.impl.OrderServiceImpl;
import ru.ifellow.jschool.ebredichina.service.impl.UserClientServiceImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@IT
@RequiredArgsConstructor
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderServiceImplIntegrTest {

    private final OrderServiceImpl orderService;
    private final OrderRepository orderRepository;
    //  private final CustomerRepository customerRepository;
    private final BookInfoRepository bookInfoRepository;
    private final OfflineBookShopRepository offlineBookShopRepository;
    private final BookStorageRepository bookStorageRepository;
    private final BookRepository bookRepository;

    @MockitoBean
    private UserClientServiceImpl userClientService;


    @BeforeEach
    void setUpSecurityContext() {
        GetUserDto principal = GetUserDto.builder()
                .email("john.doe@example.com")
                .password("$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC")
                .name("ssdd")
                .userRole(UserRole.CUSTOMER)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal.getEmail(), null,
                Collections.singleton(new SimpleGrantedAuthority(principal.getUserRole().getAuthority())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void registerOrderOnline_OK() {

        BookStorage newBookStorage = BookStorage.builder()
                .address("Main Street")
                .name("shop1")
                .totalAmount(30)
                .build();
        bookStorageRepository.save(newBookStorage);

        Book newBook = Book.builder()
                .title("Dune")
                .author("Herbert")
                .genre(BookGenre.NOVEL)
                .publishingYear("1985")
                .build();
        bookRepository.save(newBook);


        BookInfo bookInfoForSave = BookInfo.builder()
                .book(newBook)
                .amount(26)
                .price(BigDecimal.valueOf(10))
                .bookStorage(newBookStorage)
                .offlineBookShop(null)
                .build();

        bookInfoRepository.save(bookInfoForSave);

        BuyBookDto buyBookDtoTwo = new BuyBookDto(newBook.getId(), "Dune", 26);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo);

        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440011"))
                .email("john.doe@example.com")
                .name("ssdd")
                .password("$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC")
                .userRole(UserRole.CUSTOMER)
                .build();

        when(userClientService.getUserById(customer.getId())).thenReturn(customer);
        when(userClientService.getUser(customer.getEmail())).thenReturn(customer);
        // customerRepository.save(customer);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(BigDecimal.valueOf(259.74))
                .build();

        Assertions.assertTrue(orderRepository.findAll().isEmpty());
        // Customer customer1 = customerRepository.findAll().get(0);
        OnlineOrderDto onlineOrderDto = orderService.registerOrderOnline(createOrderDto);

        Assertions.assertEquals(1, orderRepository.findAll().size());
        // Assertions.assertEquals(customer1.getId(), onlineOrderDto.getCustomerId());

    }


    @Test
    void acceptPurchaseOnline() {

        BookStorage newBookStorage = BookStorage.builder()
                .address("Main Street")
                .name("shop1")
                .totalAmount(30)
                .build();
        bookStorageRepository.save(newBookStorage);

        Book newBook = Book.builder()
                .title("Dune")
                .author("Herbert")
                .genre(BookGenre.NOVEL)
                .publishingYear("1985")
                .build();
        bookRepository.save(newBook);

        BookInfo bookInfoForSave = BookInfo.builder()
                .book(newBook)
                .amount(26)
                .price(BigDecimal.valueOf(10))
                .bookStorage(newBookStorage)
                .offlineBookShop(null)
                .build();
        bookInfoRepository.save(bookInfoForSave);

        BuyBookDto buyBookDtoTwo = new BuyBookDto(newBook.getId(), "Dune", 26);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo);
        List<SoldBook> soldBooks = List.of(new SoldBook(newBook.getId(), newBook.getTitle(), 20));

//        Customer customer = Customer.builder()
//                .email("john.doe@example.com")
//                .name("ssdd")
//                .password("$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC")
//                .userRole(UserRole.CUSTOMER)
//                .build();
//
//        customerRepository.save(customer);
        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440011"))
                .email("john.doe@example.com")
                .name("ssdd")
                .password("$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC")
                .userRole(UserRole.CUSTOMER)
                .build();

        when(userClientService.getUserById(customer.getId())).thenReturn(customer);
        when(userClientService.getUser(customer.getEmail())).thenReturn(customer);


        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(BigDecimal.valueOf(260.0000).setScale(2))
                .build();

        Order order = Order.builder()
                .customer(customer.getId())
                .sum(createOrderDto.getMoneyFromCustomer())
                .status(OrderStatus.PENDING)
                .storageId(newBookStorage.getId())
                .customerBox(soldBooks)
                .build();

        orderRepository.save(order);
        Order savedOrder = orderRepository.findAll().get(0);
        CreateChequeDto createChequeDto = orderService.acceptPurchaseOnline(savedOrder.getId(), createOrderDto);

        assertNotNull(createChequeDto);
        Assertions.assertEquals(createOrderDto.getMoneyFromCustomer(), savedOrder.getSum());
        Assertions.assertEquals(savedOrder.getId(), createChequeDto.getOrderId());
        // Assertions.assertEquals(savedCus.getName(), customer.getName());
    }


    @Test
    void doPurchaseOffline_OK() {

        OfflineBookShop bookShop = OfflineBookShop.builder()
                .address("Main Street")
                .name("shop1")
                .totalAmount(30)
                .build();
        offlineBookShopRepository.save(bookShop);

        Book newBook = Book.builder()
                .title("Dune")
                .author("Herbert")
                .genre(BookGenre.NOVEL)
                .publishingYear("1985")
                .build();
        bookRepository.save(newBook);

        BookInfo bookInfoForSave = BookInfo.builder()
                .book(newBook)
                .amount(26)
                .price(BigDecimal.valueOf(10))
                .offlineBookShop(bookShop)
                .build();
        bookInfoRepository.save(bookInfoForSave);

        List<BookInfo> foundBookInfo = bookInfoRepository.findAll();
        BuyBookDto buyBookDtoTwo = new BuyBookDto(foundBookInfo.get(0).getBook().getId(), "1984", 15);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo);

        OfflineBookShop savedOfflineShop = offlineBookShopRepository.findAll().get(0);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .customerBox(customerList)
                .moneyFromCustomer(BigDecimal.valueOf(150))
                .build();

        Assertions.assertNotNull(savedOfflineShop);
        CreateChequeDto createChequeDto = orderService.doPurchaseOffline(savedOfflineShop.getId(), createOrderDto);

        assertNotNull(createChequeDto);
        assertEquals(savedOfflineShop.getId(), createChequeDto.getPurchasePlaceId());
    }
}

