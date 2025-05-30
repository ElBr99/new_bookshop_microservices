package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import ru.ifellow.jschool.dto.OfflineBookShopDto;
import ru.ifellow.jschool.dto.BookInfoDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "offline-book-shop-client", url = "${feign.book-management-service.url}")
public interface OfflineBookShopClient {

    @GetMapping("api/v1/book-shops/{shopId}/assortment")
    List<BookInfoDto> viewCurrentAssortment(@PathVariable("shopId") UUID shopId);

    @GetMapping("api/v1/book-shops/{shopId}/shop")
    OfflineBookShopDto getShop(@PathVariable("shopId") UUID shopId);

    @GetMapping("api/v1/book-shops")
    List<UUID> getAllIds();

    @GetMapping("api/v1/book-shops/book-amount/{shopId}/{bookId}")
    int getBookAmount(@PathVariable("shopId") UUID shopId, @PathVariable("bookId") UUID bookId);

    @PostMapping("api/v1/book-shops/{shopId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    void addBooks(@PathVariable("shopId") UUID shopId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList);

    @PatchMapping("api/v1/book-shops")
    void removeBook(@RequestParam UUID onlinePurchaseId, @RequestParam UUID bookId, @RequestParam int amount);

}
