package ru.ifellow.ebredichina.bookservice.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BuyBookDto {

    private UUID bookId;

    private String title;

    @Min(value = 1, message = "Корзина пуста. Чтобы оформить заказ, добавьте товар в корзину")
    private int amount;
}
