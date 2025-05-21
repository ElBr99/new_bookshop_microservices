package ru.ifellow.jschool.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.dto.FavouriteBookDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "favourite-book-client", url = "${feign.book-management-service.url}")
public interface FavouriteBookClient {

    @PostMapping("/api/v1/favourites")
    @ResponseStatus(HttpStatus.OK)
    void addToMyFavourites(@RequestParam("bookInfoId") UUID bookInfoId, @PathVariable("userId") UUID userId);

    @GetMapping("/api/v1/favourites/{userId}")
    List<FavouriteBookDto> getFavouriteBooks(@PathVariable("userId") UUID userId);

}
