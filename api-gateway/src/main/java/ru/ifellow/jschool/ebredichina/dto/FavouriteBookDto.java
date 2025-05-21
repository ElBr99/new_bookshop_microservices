package ru.ifellow.jschool.ebredichina.dto;

import ru.ifellow.jschool.ebredichina.enums.BookGenre;

import java.util.UUID;

public class FavouriteBookDto {

    private UUID favouriteBookId;
    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;

}
