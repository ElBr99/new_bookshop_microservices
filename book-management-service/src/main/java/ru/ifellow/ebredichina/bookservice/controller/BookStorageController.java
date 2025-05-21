package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.bookservice.service.BookStorageService;
import ru.ifellow.ebredichina.bookservice.service.StorageService;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.BuyBookDto;
import ru.ifellow.jschool.dto.CreateBookInfoDto;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-storages")
public class BookStorageController {

    private final StorageService<BookStorageDto> absStorageService;
    private final BookStorageService bookStorageService;

    @GetMapping("/{bookStorageId}/assortment")
    public List<BookInfoDto> viewCurrentAssortment(@PathVariable("bookStorageId") UUID bookStorageId) {
        return absStorageService.viewCurrentAssortment(bookStorageId);
    }

    @GetMapping("/{storageId}/storage")
    public BookStorageDto getStorage(@PathVariable("storageId") UUID storageId) {
        return absStorageService.getStorageById(storageId);
    }

    @GetMapping
    public List<UUID> getAllIds() {
        return absStorageService.showAllBookStorageIds();
    }

    @GetMapping("/book-amount/{storageId}/{bookId}")
    public int getBookAmount(@PathVariable("storageId") UUID storageId, @PathVariable("bookId") UUID bookId) {
        return absStorageService.getBookAmount(storageId, bookId);
    }

    @PostMapping("/{storageId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(@PathVariable("storageId") UUID storageId, @RequestBody List<CreateBookInfoDto> createBookInfoDtoList) {
        absStorageService.addBooksByPacks(storageId, createBookInfoDtoList);
    }

    @GetMapping("/delivery")
    public BookStorageDto selectsStorageForDelivery(@ModelAttribute @Validated List<BuyBookDto> customerList) {
        return bookStorageService.selectsStorageForDelivery(customerList);
    }

    @PatchMapping
    public void removeBookFromShop(@RequestParam UUID onlinePurchaseId,
                                   @RequestParam UUID bookId,
                                   @RequestParam int amount) {
        absStorageService.removeBook(onlinePurchaseId, bookId, amount);
    }


}
