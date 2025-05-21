package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.BuyBookDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "book-storage-client", url = "${feign.book-management-service.url}")
public interface BookStorageClient {

    @GetMapping("api/v1/book-storages/{bookStorageId}/assortment")
    List<BookInfoDto> viewCurrentAssortment(@PathVariable("bookStorageId") UUID bookStorageId);


    @GetMapping("api/v1/book-storages/{storageId}/storage")
    BookStorageDto getStorage(@PathVariable("storageId") UUID storageId);

    @GetMapping("api/v1/book-storages")
    List<UUID> getAllIds();

    @GetMapping("api/v1/book-storages/book-amount/{storageId}/{bookId}")
    int getBookAmount(@PathVariable("storageId") UUID storageId, @PathVariable("bookId") UUID bookId);

    @PostMapping("api/v1/book-storages/{storageId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    void addBooks(@PathVariable("storageId") UUID storageId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList);

    @GetMapping("/delivery")
    BookStorageDto selectsStorageForDelivery(@ModelAttribute @Validated List<BuyBookDto> customerList);

    @PatchMapping("api/v1/book-storages")
    void removeBook(@RequestParam UUID onlinePurchaseId, @RequestParam UUID bookId, @RequestParam int amount);
}
