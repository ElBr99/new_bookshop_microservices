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

public class CreateRequestDto {

    private UUID shopId;
    private Book book;
    private int amount;
    private BigDecimal price;
}
