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
public class BookStorageDto {

    private UUID id;
    private String address;
    private String name;
    private Integer totalAmount;
    private Map<UUID, BookInfoDto> books;

}
