package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.OfflineBookShopDto;
import ru.ifellow.ebredichina.bookservice.service.StorageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-shops")
public class OfflineBookShopController {

    private final StorageService<OfflineBookShopDto> offlineShopService;

    @GetMapping("/{shopId}/assortment")
    public List<BookInfoDto> viewCurrentAssortment(@PathVariable("shopId") UUID shopId) {
        return offlineShopService.viewCurrentAssortment(shopId);
    }

    @GetMapping("/{shopId}/shop")
    public OfflineBookShopDto getShop(@PathVariable("shopId") UUID shopId) {
        return offlineShopService.getStorageById(shopId);
    }

    @GetMapping
    public List<UUID> getAllIds() {
        return offlineShopService.showAllBookStorageIds();
    }

    @GetMapping("/book-amount/{shopId}/{bookId}")
    public int getBookAmount(@PathVariable("shopId") UUID shopId, @PathVariable("bookId") UUID bookId) {
        return offlineShopService.getBookAmount(shopId, bookId);
    }

    @PostMapping("/{shopId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(@PathVariable("shopId") UUID shopId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList) {
        offlineShopService.addBooksByPacks(shopId, createBookInfoDtoList);
    }


}
