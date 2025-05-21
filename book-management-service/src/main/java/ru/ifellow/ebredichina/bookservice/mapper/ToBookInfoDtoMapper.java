package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ToBookInfoDtoMapper {

    @Mapping(target = "bookStorage", expression = "java(bookInfo.getBookStorage() != null ? bookInfo.getBookStorage().getId() : null)")
    @Mapping(target = "offlineBookShop", expression = "java(bookInfo.getOfflineBookShop() != null ? bookInfo.getOfflineBookShop().getId() : null)")
    BookInfoDto bookInfoToBookInfoDto(BookInfo bookInfo);

    BookInfo toBookInfo(BookInfoDto bookInfoDto);

    List<BookInfoDto> listBookInfoToBookInfoDto(List<BookInfo> bookInfoList);

}
