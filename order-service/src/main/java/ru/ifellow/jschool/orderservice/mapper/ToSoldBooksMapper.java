package ru.ifellow.jschool.orderservice.mapper;

import org.mapstruct.Mapper;
import ru.ifellow.jschool.dto.BuyBookDto;
import ru.ifellow.jschool.dto.SoldBook;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ToSoldBooksMapper {

    SoldBook mapToSoldBook(BuyBookDto buyBookDto);

    List<SoldBook> mapToSoldBooks(List<BuyBookDto> buyBookDtoList);

}
