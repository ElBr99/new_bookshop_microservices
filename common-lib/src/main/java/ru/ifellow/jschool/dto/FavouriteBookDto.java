package ru.ifellow.jschool.dto;

import ru.ifellow.jschool.enums.BookGenre;
import java.util.UUID;

public class FavouriteBookDto {

    private UUID favouriteBookId;
    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;

}
