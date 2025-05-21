package ru.ifellow.jschool.ebredichina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.model.SoldBook;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OnlineOrderDto {

    private UUID customerId;
    private List<SoldBook> customerBox;
    private OrderStatus status;
    private BigDecimal sum;

}
