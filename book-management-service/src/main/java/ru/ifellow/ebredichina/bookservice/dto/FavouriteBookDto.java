package ru.ifellow.ebredichina.bookservice.dto;

import ru.ifellow.ebredichina.bookservice.enums.BookGenre;

import java.util.UUID;

public class FavouriteBookDto {

    private UUID favouriteBookId;
    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;
}
