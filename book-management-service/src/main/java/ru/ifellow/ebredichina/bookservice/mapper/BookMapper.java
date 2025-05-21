package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.bookservice.dto.SendBookInfo;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.model.FavouriteBook;


import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.genre", target = "genre")
    @Mapping(source = "book.publishingYear", target = "publishingYear")
    SendBookInfo toSendBookInfo(BookInfo bookInfo);

    default List<SendBookInfo> toSendBookInfoList(List<FavouriteBook> favouriteBooks) {
        if (favouriteBooks == null) {
            return null;
        }

        List<SendBookInfo> list = new ArrayList<>(favouriteBooks.size());
        for (FavouriteBook favouriteBook : favouriteBooks) {

            if (favouriteBook != null && favouriteBook.getBookInfoFavourite() != null) {
                list.add(toSendBookInfo(favouriteBook.getBookInfoFavourite()));
            }
        }

        return list;
    }
}
