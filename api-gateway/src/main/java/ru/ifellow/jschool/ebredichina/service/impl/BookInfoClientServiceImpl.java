package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.BookInfoClient;
import ru.ifellow.jschool.dto.BookFinderDto;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.service.BookInfoClientService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookInfoClientServiceImpl implements BookInfoClientService {

    private final BookInfoClient bookInfoClient;

    @Override
    public List<BookInfoDto> findBook(BookFinderDto bookFinderDto) {
        return bookInfoClient.findBook(bookFinderDto);
    }

    @Override
    public Map<String, List<BookInfoDto>> viewBooksByGenre() {
        return bookInfoClient.groupBooksByGenre();
    }

    @Override
    public List<BookInfoDto> filterBooksByAuthor(String author) {
        return bookInfoClient.getBooksByAuthor(author);
    }

    @Override
    public BookInfoDto getBookInfo(UUID bookInfoId) {
        return bookInfoClient.getBookById(bookInfoId);
    }


}
