package ru.ifellow.jschool.ebredichina.dto;

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

public class CreateRequestDto {

    private UUID shopId;
    private BookDto book;
    private int amount;
    private BigDecimal price;
}
