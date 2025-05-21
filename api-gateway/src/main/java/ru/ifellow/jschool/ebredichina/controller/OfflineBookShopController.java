package ru.ifellow.jschool.ebredichina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
import ru.ifellow.jschool.ebredichina.service.OfflineBookShopClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-shops")
public class OfflineBookShopController {

    private static final String KEEPER_SELLER_AUTHORITIES = "hasAnyAuthority('STOREKEEPER', 'BOOKSELLER')";
    private final OfflineBookShopClientService offlineBookShopClientService;

    @GetMapping("/{shopId}/assortment")
    public List<BookInfoDto> viewCurrentAssortment(@PathVariable("shopId") UUID shopId) {
        return offlineBookShopClientService.viewCurrentAssortment(shopId);
    }

    @GetMapping("/{shopId}/shop")
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public OfflineBookShopDto getShop(@PathVariable("shopId") UUID shopId) {
        return offlineBookShopClientService.getStorageById(shopId);
    }

    @GetMapping
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public List<UUID> getAllIds() {
        return offlineBookShopClientService.showAllBookStorageIds();
    }

    @GetMapping("/book-amount/{shopId}/{bookId}")
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public int getBookAmount(@PathVariable("shopId") UUID shopId, @PathVariable("bookId") UUID bookId) {
        return offlineBookShopClientService.getBookAmount(shopId, bookId);
    }

    @PostMapping("/{shopId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(KEEPER_SELLER_AUTHORITIES)
    public void addBooks(@PathVariable("shopId") UUID shopId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList) {
        offlineBookShopClientService.addBooksByPacks(shopId, createBookInfoDtoList);
    }


}
