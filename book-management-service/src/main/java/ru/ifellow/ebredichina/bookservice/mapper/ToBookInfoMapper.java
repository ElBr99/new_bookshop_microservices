package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ToBookInfoMapper {

    @Mapping(source = "title", target = "book.title")
    @Mapping(source = "author", target = "book.author")
    @Mapping(source = "genre", target = "book.genre")
    @Mapping(source = "publishingYear", target = "book.publishingYear")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "price", target = "price")
    BookInfo mapToBookInfo(CreateBookInfoDto createBookInfoDto);

    List<BookInfo> mapToList(List<CreateBookInfoDto> createBookInfoDtoList);


}
