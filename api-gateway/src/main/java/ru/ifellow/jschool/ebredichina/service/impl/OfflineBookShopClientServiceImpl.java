package ru.ifellow.jschool.ebredichina.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.client.OfflineBookShopClient;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
import ru.ifellow.jschool.ebredichina.service.OfflineBookShopClientService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfflineBookShopClientServiceImpl implements OfflineBookShopClientService {

    private final OfflineBookShopClient offlineBookShopClient;

    @Override
    public List<BookInfoDto> viewCurrentAssortment(UUID bookShopId) {
        return offlineBookShopClient.viewCurrentAssortment(bookShopId);
    }

    @Override
    public void addBooksByPacks(UUID bookShopId, List<CreateBookInfoDto> createBookInfoDtoList) {
        offlineBookShopClient.addBooks(bookShopId, createBookInfoDtoList);
    }

    @Override
    public OfflineBookShopDto getStorageById(UUID storageId) {
        return offlineBookShopClient.getShop(storageId);
    }

    @Override
    public List<UUID> showAllBookStorageIds() {

        return offlineBookShopClient.getAllIds();
    }

    @Override
    public int getBookAmount(UUID bookShopId, UUID bookId) {
        return offlineBookShopClient.getBookAmount(bookShopId, bookId);
    }

}
