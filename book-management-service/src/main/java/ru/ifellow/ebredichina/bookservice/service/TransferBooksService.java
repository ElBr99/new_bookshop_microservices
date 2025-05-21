package ru.ifellow.ebredichina.bookservice.service;


import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.jschool.dto.CreateRequestDto;

import java.util.List;
import java.util.UUID;

public interface TransferBooksService {

    void moveRequestedBooksFromStorageToShops(UUID bookStorageId, List<CreateRequestDto> requestedBookInfoList);

    void transferBooksToShop(UUID bookStorageId, UUID shopId, List<BookInfo> bookInfoList);

}
