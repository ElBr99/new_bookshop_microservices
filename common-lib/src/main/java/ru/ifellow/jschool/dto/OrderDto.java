package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID id;
    private String customerEmail;
    private UUID storageId;
    private UUID shopId;
    private List<SoldBook> customerBox;
    private OrderStatus status;
    private BigDecimal sum;
}
