package ru.ifellow.ebredichina.bookservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.jschool.dto.OfflineBookShopDto;


@Mapper(componentModel = "spring", uses = {ToBookInfoDtoMapper.class})
public abstract class OfflineBookShopMapper {

    @Mapping(target = "books", source = "books")
    public abstract OfflineBookShopDto toBookShopDto(OfflineBookShop offlineBookShop);

    public abstract OfflineBookShop toOfflineBookShop(OfflineBookShopDto offlineBookShopDto);

}
