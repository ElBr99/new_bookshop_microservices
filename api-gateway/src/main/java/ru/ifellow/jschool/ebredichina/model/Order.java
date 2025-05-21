package ru.ifellow.jschool.ebredichina.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@ToString(exclude = {"customer", "storageId", "customerBox"})
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "customer_id")
    private UUID customer;

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
