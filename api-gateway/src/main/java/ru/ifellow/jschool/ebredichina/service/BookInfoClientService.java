package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface BookInfoClientService {
    List<BookInfoDto> findBook(BookFinderDto bookFinderDto);

    Map<String, List<BookInfoDto>> viewBooksByGenre();

    List<BookInfoDto> filterBooksByAuthor(String author);

    BookInfoDto getBookInfo(UUID bookInfoId);

}
