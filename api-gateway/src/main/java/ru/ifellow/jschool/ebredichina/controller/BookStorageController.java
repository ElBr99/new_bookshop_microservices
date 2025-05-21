package ru.ifellow.jschool.ebredichina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.service.BookStorageClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-storages")
public class BookStorageController {

    private final BookStorageClientService bookStorageClientService;
    private static final String KEEPER_SELLER_AUTHORITIES = "hasAnyAuthority('STOREKEEPER', 'BOOKSELLER')";

    @GetMapping("/{bookStorageId}/assortment")
    public List<BookInfoDto> viewCurrentAssortment(@PathVariable("bookStorageId") UUID bookStorageId) {
        return bookStorageClientService.viewCurrentAssortment(bookStorageId);
    }

    @GetMapping("/{storageId}/storage")
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public BookStorageDto getStorage(@PathVariable("storageId") UUID storageId) {
        return bookStorageClientService.getStorageById(storageId);
    }

    @GetMapping
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public List<UUID> getAllIds() {
        return bookStorageClientService.showAllBookStorageIds();
    }

    @GetMapping("/book-amount/{storageId}/{bookId}")
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public int getBookAmount(@PathVariable("storageId") UUID storageId, @PathVariable("bookId") UUID bookId) {
        return bookStorageClientService.getBookAmount(storageId, bookId);
    }

    @PostMapping("/{storageId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public void addBooks(@PathVariable("storageId") UUID storageId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList) {
        bookStorageClientService.addBooksByPacks(storageId, createBookInfoDtoList);
    }


}
