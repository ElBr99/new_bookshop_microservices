package ru.ifellow.jschool.ebredichina.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import ru.ifellow.jschool.client.TransferBooksClient;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateRequestDto;
import ru.ifellow.jschool.ebredichina.service.TransferBooksClientService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferBooksClientServiceImpl implements TransferBooksClientService {

    private final TransferBooksClient transferBooksClient;


    @Override
    public void moveRequestedBooksFromStorageToShops(UUID bookStorageId, List<CreateRequestDto> requestedBookInfoList) {
        transferBooksClient.moveRequestedBooksFromStorageToShops(bookStorageId, requestedBookInfoList);
    }

    @Override
    public void transferBooksToShop(UUID bookStorageId, UUID shopId, List<BookInfoDto> bookInfoList) {
        transferBooksClient.moveBooks(bookStorageId, shopId, bookInfoList);
    }


}
