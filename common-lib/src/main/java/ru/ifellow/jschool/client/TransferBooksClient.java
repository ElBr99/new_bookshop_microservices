package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ifellow.jschool.dto.BookInfoDto;
import ru.ifellow.jschool.dto.CreateRequestDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "transfer-book-client", url = "${feign.book-management-service.url}")
public interface TransferBooksClient {

    @PutMapping("api/v1/transferBooks/moveRequestedBooks")
    @ResponseStatus(HttpStatus.OK)
    void moveRequestedBooksFromStorageToShops(@PathVariable UUID bookStorageId, @RequestBody List<CreateRequestDto> requestDtoList);

    @PutMapping("api/v1/transferBooks/books-transfer")
    @ResponseStatus(HttpStatus.OK)
    void moveBooks(@PathVariable UUID bookStorageId, @PathVariable UUID bookShopId, @RequestBody List<BookInfoDto> bookInfoDtos);
}
