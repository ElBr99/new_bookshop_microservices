package ru.ifellow.jschool.ebredichina.dto;

import ru.ifellow.jschool.ebredichina.enums.BookGenre;

import java.util.UUID;

public class BookDto {

    private UUID id;
    private String title;
    private String author;
    private BookGenre genre;
    private String publishingYear;
}
