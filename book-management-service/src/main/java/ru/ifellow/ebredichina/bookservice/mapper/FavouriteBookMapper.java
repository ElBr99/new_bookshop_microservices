package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.ifellow.ebredichina.bookservice.model.FavouriteBook;
import ru.ifellow.jschool.dto.FavouriteBookDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavouriteBookMapper {


    @Mapping(target = "bookInfoId", source = "id.bookInfoId")
    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "title", source = "bookInfoFavourite.book.title")
    @Mapping(target = "author", source = "bookInfoFavourite.book.author")
    @Mapping(target = "genre", source = "bookInfoFavourite.book.genre")
    @Mapping(target = "publishingYear", source = "bookInfoFavourite.book.publishingYear")
    FavouriteBookDto mapToFavouriteBookDto (FavouriteBook favouriteBook);

    List<FavouriteBookDto> mapToFavorBookDtoList (List<FavouriteBook> favouriteBooks);


}
