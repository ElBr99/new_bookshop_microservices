package ru.ifellow.jschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.ChequeType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChequeDto {

    private UUID orderId;
    private String customerEmail;
    private UUID purchasePlaceId;
    private List<SoldBook> customerBox;
    private BigDecimal sum;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private OffsetDateTime purchaseDate;
    private ChequeType chequeType;

}
