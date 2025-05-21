package integrationTests.repository;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.enums.UserRole;
import ru.ifellow.jschool.ebredichina.model.Order;
import ru.ifellow.jschool.ebredichina.model.SoldBook;
import ru.ifellow.jschool.ebredichina.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderRepositoryImplTest {

    private final OrderRepository orderRepository;

    @Test
    void saveOrderTest_OK() {

        List<SoldBook> soldBooks = List.of(new SoldBook(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 4));
        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("securepassword")
                .userRole(UserRole.CUSTOMER)
                .build();


        Order expectedOrder = Order.builder()
                .customer(customer.getId())
                .status(OrderStatus.ACCEPTED)
                .customerBox(soldBooks)
                .sum(BigDecimal.valueOf(9.99).multiply(BigDecimal.valueOf(2)))
                .storageId(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();

        Order savedOrder = orderRepository.save(expectedOrder);
        Optional<Order> orderFromRepo = orderRepository.findById(savedOrder.getId());


        Assertions.assertFalse(orderFromRepo.isEmpty());
        assertNotNull(orderFromRepo.get());
        assertEquals(expectedOrder.getId(), savedOrder.getId());
    }

    @Test
    void testUpdateOrder_OK() {

        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("securepassword")
                .userRole(UserRole.CUSTOMER)
                .build();
        List<SoldBook> soldBooks = List.of(new SoldBook(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 3));

        Order orderForUpdate = Order.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440014"))
                .customer(customer.getId())
                .storageId(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .status(OrderStatus.PENDING)
                .sum(BigDecimal.valueOf(15.98))
                .customerBox(soldBooks)
                .build();

        orderForUpdate.setStatus(OrderStatus.ACCEPTED);

        Order updatedOrder = orderRepository.save(orderForUpdate);

        assertEquals(OrderStatus.ACCEPTED, updatedOrder.getStatus());
    }

    @Test
    void GetOrderByParamsTest_OK() {

        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440012");
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655440014");

        Optional<Order> result = orderRepository.getOrderByParams(customerId, orderId, OrderStatus.PENDING);

        assertFalse(result.isEmpty());
        assertNotNull(result);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"), result.get().getCustomer());
        assertEquals(OrderStatus.PENDING, result.get().getStatus());

    }

    @Test
    void GetOrderByParamsTest_NonExistingOrder_ReturnsNull() {
        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440012");
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655441111");

        Optional<Order> result = orderRepository.getOrderByParams(customerId, orderId, OrderStatus.PENDING);

        assertTrue(result.isEmpty());

    }

    @Test
    void GetOrderByParamsTest_NonExistingCustomer_ReturnsNull() {
        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655444444");
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655440014");

        Optional<Order> result = orderRepository.getOrderByParams(customerId, orderId, OrderStatus.PENDING);

        assertTrue(result.isEmpty());
    }

    @Test
    void FindById_ExistingOrderId_ReturnsOrder() {
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655440014");

        Optional<Order> foundOrder = orderRepository.findById(orderId);

        Assertions.assertFalse(foundOrder.isEmpty());
        assertEquals(orderId, foundOrder.get().getId());
    }

    @Test
    void FindById_NonExistingOrderId_ReturnsNull() {
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655444444");

        Optional<Order> foundOrder = orderRepository.findById(orderId);

        Assertions.assertTrue(foundOrder.isEmpty());
    }
}

