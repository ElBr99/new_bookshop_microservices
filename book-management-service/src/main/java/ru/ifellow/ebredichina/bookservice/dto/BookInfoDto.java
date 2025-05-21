package ru.ifellow.ebredichina.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.ebredichina.bookservice.model.Book;


import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoDto {

    private UUID id;
    private Book book;
    private int amount;
    private BigDecimal price;
    private UUID bookStorage;
    private UUID offlineBookShop;

}
