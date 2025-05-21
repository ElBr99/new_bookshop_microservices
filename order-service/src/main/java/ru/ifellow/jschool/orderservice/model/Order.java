package ru.ifellow.jschool.orderservice.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import ru.ifellow.jschool.dto.SoldBook;
import ru.ifellow.jschool.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@ToString(exclude = {"customerBox"})
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    private UUID customerId;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "book_storage_id")
    private UUID storageId;

    @Column(name = "book_shop_id")
    private UUID shopId;

    @Type(value = JsonType.class)
    @Column(name = "customer_box", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<SoldBook> customerBox;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal sum;

}
