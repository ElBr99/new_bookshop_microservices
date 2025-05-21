package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"book", "bookStorage", "offlineBookShop"})
@Table(name = "book_info")
public class BookInfo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private int amount;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "storage_id")
    private BookStorage bookStorage;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "offline_book_shop_id")
    private OfflineBookShop offlineBookShop;


    public BigDecimal countBookPrice(BigDecimal price, int amount) {
        return price.multiply(BigDecimal.valueOf(amount));
    }

}
