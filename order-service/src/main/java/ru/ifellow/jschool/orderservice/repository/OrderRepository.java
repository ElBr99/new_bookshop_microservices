package ru.ifellow.jschool.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifellow.jschool.enums.OrderStatus;
import ru.ifellow.jschool.orderservice.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o  WHERE o.customer = :customerId AND o.status = :status AND o.id = :orderId")
    Optional<Order> getOrderByParams(@Param("customerId") UUID customerId, @Param("orderId") UUID orderId, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o  WHERE o.customer = :customerId")
    List<Order> getOrdersByCustomer(@Param("customerId") UUID customerId);

}
