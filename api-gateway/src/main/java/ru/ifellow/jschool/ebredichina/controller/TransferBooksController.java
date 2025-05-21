package ru.ifellow.jschool.ebredichina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateRequestDto;
import ru.ifellow.jschool.ebredichina.service.TransferBooksClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transferBooks")
public class TransferBooksController {

    private final TransferBooksClientService transferBooksService;

    @PutMapping("/moveRequestedBooks")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public void moveRequestedBooksFromStorageToShops(@PathVariable UUID bookStorageId, @RequestBody List<CreateRequestDto> requestDtoList) {
        transferBooksService.moveRequestedBooksFromStorageToShops(bookStorageId, requestDtoList);
    }

    @PutMapping("/books-transfer")
    @ResponseStatus(HttpStatus.OK)
    public void moveBooks (@PathVariable UUID bookStorageId, @PathVariable UUID bookShopId, @RequestBody List<BookInfoDto> bookInfoDtos) {
        transferBooksService.transferBooksToShop(bookStorageId, bookShopId, bookInfoDtos);
    }


}
