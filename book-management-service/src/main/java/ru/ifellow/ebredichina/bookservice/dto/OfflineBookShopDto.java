package ru.ifellow.ebredichina.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfflineBookShopDto {
    protected UUID id;
    protected String address;
    protected String name;
    protected Integer totalAmount;
    private Map<UUID, BookInfoDto> books;
}
