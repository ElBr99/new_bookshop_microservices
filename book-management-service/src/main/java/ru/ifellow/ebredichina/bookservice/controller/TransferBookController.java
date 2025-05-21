package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.bookservice.dto.CreateRequestDto;
import ru.ifellow.ebredichina.bookservice.service.TransferBooksService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transferBooks")
public class TransferBookController {

    private final TransferBooksService transferBooksService;

    @PutMapping("/moveRequestedBooks")
    @ResponseStatus(HttpStatus.OK)
    public void moveRequestedBooksFromStorageToShops(@PathVariable UUID bookStorageId, @RequestBody List<CreateRequestDto> requestDtoList) {
        transferBooksService.moveRequestedBooksFromStorageToShops(bookStorageId, requestDtoList);
    }


}
