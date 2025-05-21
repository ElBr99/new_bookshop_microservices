package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.bookservice.service.StorageService;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
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

    @PatchMapping
    public void removeBookFromShop(@RequestParam UUID onlinePurchaseId, @RequestParam UUID bookId, @RequestParam int amount) {
        offlineShopService.removeBook(onlinePurchaseId, bookId, amount);
    }

}
