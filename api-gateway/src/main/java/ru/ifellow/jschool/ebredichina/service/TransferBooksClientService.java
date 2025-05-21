package ru.ifellow.jschool.ebredichina.service;

import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.CreateRequestDto;


import java.util.List;
import java.util.UUID;

public interface TransferBooksClientService {

    void moveRequestedBooksFromStorageToShops(UUID bookStorageId, List<CreateRequestDto> requestedBookInfoList);

    void transferBooksToShop(UUID bookStorageId, UUID shopId, List<BookInfoDto> bookInfoList);

}
