package ru.ifellow.ebredichina.bookservice.service;

import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;

import java.util.List;
import java.util.UUID;

public interface StorageService<T> {

    List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId);

    void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList);

    T getStorageById(UUID storageId);

    boolean isEnoughBooks(UUID bookId, int amount, UUID bookStorageId);

    List<UUID> showAllBookStorageIds();

    int getBookAmount(UUID bookShopId, UUID bookId);

}
