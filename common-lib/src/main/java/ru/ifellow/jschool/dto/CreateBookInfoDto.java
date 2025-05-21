package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.BookGenre;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateBookInfoDto {

    private String title;
    private String author;
    private BookGenre genre;
    private int amount;
    private BigDecimal price;
    private String publishingYear;
}
