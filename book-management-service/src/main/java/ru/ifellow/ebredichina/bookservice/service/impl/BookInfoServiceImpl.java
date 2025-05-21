package ru.ifellow.ebredichina.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.ebredichina.bookservice.dto.BookFinderDto;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.exception.BookNotFoundException;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoDtoMapper;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.repository.BookInfoRepository;
import ru.ifellow.ebredichina.bookservice.service.BookInfoService;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookInfoServiceImpl implements BookInfoService {

    private final BookInfoRepository bookInfoRepository;
    private final ToBookInfoDtoMapper toBookInfoDtoMapper;

    @Override
    public List<BookInfoDto> findBook(BookFinderDto bookFinderDto) {
        List<BookInfo> foundBooks = bookInfoRepository.findBookByAuthorOrTitle(bookFinderDto.getBookTitle(), bookFinderDto.getAuthor());
        return toBookInfoDtoMapper.listBookInfoToBookInfoDto(foundBooks);
    }

    @Override
    public Map<String, List<BookInfoDto>> viewBooksByGenre() {
        List<BookInfo> allBooks = bookInfoRepository.findAll();
        List<BookInfoDto> mappedAllBooks = toBookInfoDtoMapper.listBookInfoToBookInfoDto(allBooks);
        return mappedAllBooks.stream().collect(Collectors.groupingBy(bookInfo -> bookInfo.getBook().getGenre().name()));

    }

    @Override
    public List<BookInfoDto> filterBooksByAuthor(String author) {
        List<BookInfo> bookInfos = bookInfoRepository.findByAuthor(author);
        return toBookInfoDtoMapper.listBookInfoToBookInfoDto(bookInfos);
    }

    @Override
    public BookInfoDto getBookInfo(UUID bookInfoId) {
        BookInfo bookInfo = bookInfoRepository.findById(bookInfoId).orElseThrow(()->new BookNotFoundException("Такой книги не найдено"));
        return toBookInfoDtoMapper.bookInfoToBookInfoDto(bookInfo);
    }


}
