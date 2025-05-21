package ru.ifellow.ebredichina.bookservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifellow.ebredichina.bookservice.exception.ExceptionThrower;
import ru.ifellow.ebredichina.bookservice.model.AbsStorage;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
public class CommonStorageService<T extends AbsStorage> {

    private final JpaRepository<T, UUID> commonRepository;
    private final ExceptionThrower exceptionThrower;


    public List<BookInfo> viewCurrentAssortment(UUID bookStorageId) {
        return commonRepository
                .findById(bookStorageId)
                .orElseThrow(exceptionThrower::getNotFound)
                .getBooks()
                .values()
                .stream()
                .toList();
    }

    public void addBooksByPacks(UUID storageId, List<BookInfo> bookInfos) {

        bookInfos.forEach(bookInfo -> commonRepository
                .findById(storageId)
                .orElseThrow(exceptionThrower::getNotFound)
                .addBook(bookInfo)
        );
    }

    public T getStorageById(UUID storageId) {
        return commonRepository
                .findById(storageId)
                .orElseThrow(exceptionThrower::getNotFound);
    }


    public boolean isEnoughBooks(UUID bookId, int amount, UUID bookStorageId) {
        return commonRepository
                .findById(bookStorageId)
                .orElseThrow(exceptionThrower::getNotFound)
                .getBooks()
                .values()
                .stream()
                .anyMatch(bookInfo -> bookInfo.getAmount() >= amount && bookInfo.getBook().getId().equals(bookId));
    }


    public List<UUID> showAllBookStorageIds() {
        return commonRepository
                .findAll()
                .stream()
                .map(AbsStorage::getId)
                .toList();
    }


    public int getBookAmount(UUID bookShopId, UUID bookId) {
        return commonRepository
                .findById(bookShopId)
                .orElseThrow(exceptionThrower::getNotFound)
                .getBooks()
                .get(bookId)
                .getAmount();
    }

}
