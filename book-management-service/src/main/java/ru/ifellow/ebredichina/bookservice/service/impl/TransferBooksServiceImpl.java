package ru.ifellow.ebredichina.bookservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.ebredichina.bookservice.dto.CreateRequestDto;
import ru.ifellow.ebredichina.bookservice.exception.NotEnoughBooksException;
import ru.ifellow.ebredichina.bookservice.mapper.RequestBookInfoToBookInfoMapper;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.ebredichina.bookservice.service.TransferBooksService;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferBooksServiceImpl implements TransferBooksService {

    private final CommonStorageService<OfflineBookShop> offlineBookShopService;
    private final CommonStorageService<BookStorage> bookStorageService;
    private final RequestBookInfoToBookInfoMapper requestBookInfoToBookInfoMapper;


    @Override
    public void moveRequestedBooksFromStorageToShops(UUID bookStorageId, List<CreateRequestDto> requestedBookInfoList) {

        BookStorage bookStorage = bookStorageService.getStorageById(bookStorageId);

        for (CreateRequestDto request : requestedBookInfoList) {
            UUID bookId = request.getBook().getId();
            int requestedAmount = request.getAmount();
            UUID shopId = request.getShopId();

            BookInfo bookInfo = requestBookInfoToBookInfoMapper.mapToBookInfo(request);

            int availableAmount = getBookAmount(bookStorageId, bookId);

            OfflineBookShop offlineBookShop = offlineBookShopService.getStorageById(shopId);

            if (bookStorageService.isEnoughBooks(bookId, requestedAmount, bookStorageId)) {
                bookStorage.removeBook(bookId, requestedAmount);
                offlineBookShop.addBook(bookInfo);

            } else if (requestedAmount > availableAmount) {
                bookStorage.removeBook(bookId, availableAmount);
                bookInfo.setAmount(availableAmount);
                offlineBookShop.addBook(bookInfo);
            }

            if (availableAmount == 0) {
                throw new NotEnoughBooksException("Книг нет на складе");
            }
        }
    }


    @Override
    public void transferBooksToShop(UUID bookStorageId, UUID shopId, List<BookInfo> bookInfoList) {
        BookStorage bookStorage = bookStorageService.getStorageById(bookStorageId);
        OfflineBookShop offlineBookShop = offlineBookShopService.getStorageById(shopId);
        for (BookInfo bookInfo : bookInfoList) {
            offlineBookShop.addBook(bookInfo);
            bookStorage.removeBook(bookInfo.getBook().getId(), bookInfo.getAmount());
        }
    }

    public int getBookAmount(UUID bookStorageId, UUID bookId) {
        return bookStorageService.getStorageById(bookStorageId)
                .getBooks().values().stream()
                .filter(bookInfo -> bookInfo.getBook().getId().equals(bookId))
                .mapToInt(BookInfo::getAmount)
                .sum();
    }


}
