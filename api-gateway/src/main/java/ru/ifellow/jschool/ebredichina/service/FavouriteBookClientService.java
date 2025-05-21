package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.FavouriteBookDto;

import java.util.List;
import java.util.UUID;

public interface FavouriteBookClientService {

    void addBooksToFavorites(UUID bookInfoId);

    List<FavouriteBookDto> getFavouriteBooks();

}
