package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.BookGenre;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendBookInfo {

    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;
}
