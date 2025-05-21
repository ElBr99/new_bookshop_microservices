package ru.ifellow.ebredichina.bookservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.FavouriteBookDto;
import ru.ifellow.ebredichina.bookservice.mapper.FavouriteBookMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoDtoMapper;
import ru.ifellow.ebredichina.bookservice.model.FavouriteBook;
import ru.ifellow.ebredichina.bookservice.model.FavouriteBookId;
import ru.ifellow.ebredichina.bookservice.repository.FavouriteBookRepository;
import ru.ifellow.ebredichina.bookservice.service.BookInfoService;
import ru.ifellow.ebredichina.bookservice.service.FavouriteBookService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteBookServiceImpl implements FavouriteBookService {

    private final FavouriteBookRepository favouriteBookRepository;
    private final BookInfoService bookInfoService;
    private final ToBookInfoDtoMapper toBookInfoDtoMapper;
    private final FavouriteBookMapper favouriteBookMapper;


    @Override
    public void addBooksToFavorites(UUID bookInfoId, UUID userId) {

        BookInfoDto bookInfo = bookInfoService.getBookInfo(bookInfoId);

        FavouriteBookId favouriteBookId = new FavouriteBookId(userId, bookInfoId);
        FavouriteBook favouriteBook = FavouriteBook.builder()
                .id(favouriteBookId)
                .userId(userId)
                .bookInfoFavourite(toBookInfoDtoMapper.toBookInfo(bookInfo))
                .build();
        favouriteBookRepository.save(favouriteBook);
    }

    @Override
    public List<FavouriteBookDto> getFavouriteBooks() {
        return List.of();
    }

    @Override
    public List<FavouriteBookDto> getFavouriteBooksByUserId(UUID userId) {
        List<FavouriteBook> favouriteBooks = favouriteBookRepository.findFavouriteBooksByUserId(userId);
        return favouriteBookMapper.mapToFavorBookDtoList(favouriteBooks);

    }

}
