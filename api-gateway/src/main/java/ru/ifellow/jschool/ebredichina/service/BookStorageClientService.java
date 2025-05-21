package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import java.util.List;
import java.util.UUID;

public interface BookStorageClientService {

    List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId);

    void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList);

    BookStorageDto getStorageById(UUID storageId);

    List<UUID> showAllBookStorageIds();

    int getBookAmount(UUID bookShopId, UUID bookId);
}
