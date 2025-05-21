package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.jschool.dto.BookStorageDto;


@Mapper(componentModel = "spring", uses = {ToBookInfoDtoMapper.class})
public interface BookStorageMapper {

    @Mapping(target = "books", source = "books")
    BookStorageDto toBookStorageDto(BookStorage bookStorage);

}
