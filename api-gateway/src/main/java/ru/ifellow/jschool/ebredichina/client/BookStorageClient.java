package ru.ifellow.jschool.ebredichina.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.BookStorageDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "book-management-service", url = "http://localhost:7979")
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


}
