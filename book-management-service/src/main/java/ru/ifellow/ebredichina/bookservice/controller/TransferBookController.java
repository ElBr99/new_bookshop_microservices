package ru.ifellow.ebredichina.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.bookservice.service.TransferBooksService;
import ru.ifellow.jschool.dto.CreateRequestDto;
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
