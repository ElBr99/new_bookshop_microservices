package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.BookStorageClient;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.service.BookStorageClientService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookStorageClientServiceImpl implements BookStorageClientService {

    private final BookStorageClient bookStorageClient;

    public List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId) {
        return bookStorageClient.viewCurrentAssortment(bookStorageId);
    }

    @Override
    public void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList) {
        bookStorageClient.addBooks(storageId, createBookInfoDtoList);
    }

    @Override
    public BookStorageDto getStorageById(UUID storageId) {
        return bookStorageClient.getStorage(storageId);
    }

    @Override
    public List<UUID> showAllBookStorageIds() {
        return bookStorageClient.getAllIds();
    }

    @Override
    public int getBookAmount(UUID bookShopId, UUID bookId) {
        return bookStorageClient.getBookAmount(bookShopId, bookId);
    }


}
