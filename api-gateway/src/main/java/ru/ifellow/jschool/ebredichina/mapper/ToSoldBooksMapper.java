package ru.ifellow.jschool.ebredichina.mapper;

import org.mapstruct.Mapper;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.model.SoldBook;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToSoldBooksMapper {

    SoldBook mapToSoldBook(BuyBookDto buyBookDto);
    List<SoldBook> mapToSoldBooks(List<BuyBookDto> buyBookDtoList);

}
