package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.bookservice.dto.BookStorageDto;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;


@Mapper(componentModel = "spring", uses = {ToBookInfoDtoMapper.class})
public interface BookStorageMapper {

    @Mapping(target = "books", source = "books")
    BookStorageDto toBookStorageDto(BookStorage bookStorage);

}
