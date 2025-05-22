package ru.ifellow.jschool.orderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.ChequeType;
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
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "offline_purchase_id")
    private UUID offlinePurchaseId;

    @Column(name = "online_purchase_id")
    private UUID onlinePurchaseId;

    @Column(name = "customer_email")
    private String customerEmail;

    private BigDecimal sum;

    @Column(name = "purchase_date")
    private OffsetDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cheque_type")
    private ChequeType chequeType;


}
