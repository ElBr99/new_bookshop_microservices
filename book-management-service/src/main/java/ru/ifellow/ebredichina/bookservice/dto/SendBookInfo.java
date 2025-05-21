package ru.ifellow.ebredichina.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.ebredichina.bookservice.enums.BookGenre;

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
