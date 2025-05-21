package ru.ifellow.jschool.ebredichina.service;


import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
import java.util.List;
import java.util.UUID;

public interface OfflineBookShopClientService {

    List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId);

    void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList);

    OfflineBookShopDto getStorageById(UUID storageId);

    List<UUID> showAllBookStorageIds();

    int getBookAmount(UUID bookShopId, UUID bookId);
}
