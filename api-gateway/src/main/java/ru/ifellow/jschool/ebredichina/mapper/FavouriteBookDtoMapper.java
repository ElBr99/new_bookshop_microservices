package ru.ifellow.jschool.ebredichina.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.jschool.ebredichina.dto.FavouriteBookDto;
import ru.ifellow.jschool.ebredichina.dto.SendBookInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavouriteBookDtoMapper {


    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "publishingYear", source = "publishingYear")
    SendBookInfo mapToSendBookInfo (FavouriteBookDto favouriteBookDto);

    List<SendBookInfo> mapToSendBookInfoList (List<FavouriteBookDto> favouriteBookDtoList);

}
