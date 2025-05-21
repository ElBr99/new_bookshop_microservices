package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.BookGenre;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteBookDto {

    private UUID favouriteBookId;
    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;

}
