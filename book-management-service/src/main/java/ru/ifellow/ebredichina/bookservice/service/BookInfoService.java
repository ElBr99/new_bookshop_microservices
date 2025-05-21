package ru.ifellow.ebredichina.bookservice.service;



import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.jschool.dto.BookFinderDto;
import ru.ifellow.jschool.dto.BookInfoDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BookInfoService {
    List<BookInfoDto> findBook(BookFinderDto bookFinderDto);

    Map<String, List<BookInfoDto>> viewBooksByGenre();

    List<BookInfoDto> filterBooksByAuthor(String author);

    BookInfoDto getBookInfo(UUID bookInfoId);

}
