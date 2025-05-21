package ru.ifellow.jschool.ebredichina.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "cheques")
public class Cheque {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "offline_purchase_id", referencedColumnName = "id")
    private OfflineBookShop offlineShopPurchase;

    @ManyToOne
    @JoinColumn(name = "online_purchase_id")
    private BookStorage onlineShopPurchase;

    @Column(name = "customer_email")
    private String customerEmail;

    private BigDecimal sum;

    @Column(name = "purchase_date")
    private OffsetDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cheque_type")
    private ChequeType chequeType;


}
