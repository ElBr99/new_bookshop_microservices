package ru.ifellow.ebredichina.bookservice.service;
import ru.ifellow.ebredichina.bookservice.dto.FavouriteBookDto;

import java.util.List;
import java.util.UUID;

public interface FavouriteBookService {

    void addBooksToFavorites(UUID bookInfoId, UUID userId);

    List<FavouriteBookDto> getFavouriteBooks();

    List<FavouriteBookDto> getFavouriteBooksByUserId(UUID userId);

}
