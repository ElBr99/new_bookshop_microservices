package ru.ifellow.ebredichina.bookservice.service;


import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import java.util.List;
import java.util.UUID;

public interface StorageService<T> {

    List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId);

    void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList);

    T getStorageById(UUID storageId);

    boolean isEnoughBooks(UUID bookId, int amount, UUID bookStorageId);

    List<UUID> showAllBookStorageIds();

    int getBookAmount(UUID bookShopId, UUID bookId);

    void removeBook(UUID onlinePurchaseId, UUID bookId, int amount);

}
