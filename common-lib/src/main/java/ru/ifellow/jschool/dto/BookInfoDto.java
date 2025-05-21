package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoDto {

    private UUID id;
    private BookDto book;
    private int amount;
    private BigDecimal price;
    private UUID bookStorage;
    private UUID offlineBookShop;

}
