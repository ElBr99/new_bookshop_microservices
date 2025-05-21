package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.BookStorageDto;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;
import ru.ifellow.ebredichina.bookservice.service.StorageService;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-storages")
public class BookStorageController {

    private final StorageService<BookStorageDto> absStorageService;

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


}
