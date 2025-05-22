package ru.ifellow.ebredichina.bookservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.bookservice.mapper.OfflineBookShopMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoDtoMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoMapper;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.ebredichina.bookservice.service.StorageService;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;

import java.util.List;
import java.util.UUID;

@Lazy
@Service
@Transactional
public class OfflineBookShopServiceImpl implements StorageService<OfflineBookShopDto> {

    @Autowired
    @Qualifier("offlineBookShopDtoCommonStorageService")
    private CommonStorageService<OfflineBookShop> offlineBookShopDtoCommonStorageService;

    @Autowired
    private ToBookInfoMapper toBookInfoMapper;
    @Autowired
    private ToBookInfoDtoMapper toBookInfoDtoMapper;

    @Autowired
    private OfflineBookShopMapper offlineBookShopMapper;

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

    @Override
    public void removeBook(UUID onlinePurchaseId, UUID bookId, int amount) {
        offlineBookShopDtoCommonStorageService.removeBookFromStorage(onlinePurchaseId, bookId, amount);
    }

}
