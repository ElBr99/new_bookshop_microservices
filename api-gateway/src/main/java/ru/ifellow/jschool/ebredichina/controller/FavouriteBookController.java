package ru.ifellow.jschool.ebredichina.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.ifellow.jschool.ebredichina.service.FavouriteBookClientService;
import ru.ifellow.jschool.ebredichina.service.impl.SseEmitterService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favourites")
public class FavouriteBookController {

    private final FavouriteBookClientService favouriteBookService;
    private final SseEmitterService sseEmitterService;
    private static final String CUSTOMER_AUTHORITY = "hasAuthority('CUSTOMER')";

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(CUSTOMER_AUTHORITY)
    public void addToMyFavourites(@RequestParam("bookInfoId") UUID bookInfoId) {
        favouriteBookService.addBooksToFavorites(bookInfoId);
    }

    @PostMapping("/subscribes")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribeCustomer() {
        return sseEmitterService.createSseEmitter();
    }



}
