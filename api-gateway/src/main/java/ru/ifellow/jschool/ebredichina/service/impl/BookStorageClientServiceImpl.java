package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.ebredichina.client.BookStorageClient;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.BookStorageDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookStorageClientServiceImpl implements BookStorageClient {

    private final BookStorageClient bookStorageClient;

    @Override
    public List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId) {
        return bookStorageClient.viewCurrentAssortment(bookStorageId);
    }

    @Override
    public BookStorageDto getStorage(UUID storageId) {
        return bookStorageClient.getStorage(storageId);
    }

    @Override
    public List<UUID> getAllIds() {
        return bookStorageClient.getAllIds();
    }

    @Override
    public int getBookAmount(UUID storageId, UUID bookId) {
        return bookStorageClient.getBookAmount(storageId, bookId);
    }

    @Override
    public void addBooks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList) {
        bookStorageClient.addBooks(storageId, createBookInfoDtoList);
    }




}
