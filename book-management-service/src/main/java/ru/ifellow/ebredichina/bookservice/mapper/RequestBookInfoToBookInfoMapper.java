package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.bookservice.dto.CreateRequestDto;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;


@Mapper(componentModel = "spring")
public interface RequestBookInfoToBookInfoMapper {

    @Mapping(source = "book.title", target = "book.title")
    @Mapping(source = "book.author", target = "book.author")
    @Mapping(source = "book.genre", target = "book.genre")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "price", target = "price")
    BookInfo mapToBookInfo(CreateRequestDto createRequestDto);

}
