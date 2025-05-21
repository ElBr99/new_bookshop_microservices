package ru.ifellow.jschool.ebredichina.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.ebredichina.dto.FavouriteBookDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "book-management-service", url = "http://localhost:7979")
public interface FavouriteBookClient {

    @PostMapping("/api/v1/favourites")
    @ResponseStatus(HttpStatus.OK)
    void addToMyFavourites(@RequestParam("bookInfoId") UUID bookInfoId, @PathVariable("userId") UUID userId);

    @GetMapping("/api/v1/favourites/{userId}")
    List<FavouriteBookDto> getFavouriteBooks(@PathVariable("userId") UUID userId);

}
