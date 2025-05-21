package ru.ifellow.jschool.ebredichina.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {

    private List<BuyBookDto> customerBox;

    @NotBlank
    private BigDecimal moneyFromCustomer;

}
