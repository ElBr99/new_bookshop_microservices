package ru.ifellow.ebredichina.bookservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.OfflineBookShopDto;
import ru.ifellow.ebredichina.bookservice.mapper.OfflineBookShopMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoDtoMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoMapper;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.ebredichina.bookservice.service.StorageService;

import java.util.List;
import java.util.UUID;

@Lazy
@Service
@Transactional
@RequiredArgsConstructor
public class OfflineBookShopServiceImpl implements StorageService<OfflineBookShopDto> {

    private final CommonStorageService<OfflineBookShop> offlineBookShopDtoCommonStorageService;
    private final ToBookInfoMapper toBookInfoMapper;
    private final ToBookInfoDtoMapper toBookInfoDtoMapper;
    private final OfflineBookShopMapper offlineBookShopMapper;

    @Override
    public List<BookInfoDto> viewCurrentAssortment(UUID bookShopId) {
        List<BookInfo> foundList = offlineBookShopDtoCommonStorageService.viewCurrentAssortment(bookShopId);
        return toBookInfoDtoMapper.listBookInfoToBookInfoDto(foundList);

    }

    @Override
    public void addBooksByPacks(UUID bookShopId, List<CreateBookInfoDto> createBookInfoDtoList) {
        offlineBookShopDtoCommonStorageService.addBooksByPacks(
                bookShopId,
                toBookInfoMapper.mapToList(createBookInfoDtoList)
        );
    }

    @Override
    public OfflineBookShopDto getStorageById(UUID storageId) {
        OfflineBookShop foundBookShop = offlineBookShopDtoCommonStorageService.getStorageById(storageId);
        return offlineBookShopMapper.toBookShopDto(foundBookShop);
    }

    @Override
    public boolean isEnoughBooks(UUID bookId, int amount, UUID bookShopId) {
        return offlineBookShopDtoCommonStorageService.isEnoughBooks(bookId, amount, bookShopId);
    }

    @Override
    public List<UUID> showAllBookStorageIds() {
        return offlineBookShopDtoCommonStorageService.showAllBookStorageIds();
    }

    @Override
    public int getBookAmount(UUID bookShopId, UUID bookId) {
        return offlineBookShopDtoCommonStorageService.getBookAmount(bookShopId, bookId);
    }

}
