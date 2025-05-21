package ru.ifellow.ebredichina.bookservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.bookservice.dto.FavouriteBookDto;
import ru.ifellow.ebredichina.bookservice.service.FavouriteBookService;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favourites")
public class FavouriteBookController {

    private final FavouriteBookService favouriteBookService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addToMyFavourites(@RequestParam("bookInfoId") UUID bookInfoId, @PathVariable("userId") UUID userId){
        favouriteBookService.addBooksToFavorites(bookInfoId, userId);
    }

    @GetMapping("/{userId}")
    public List<FavouriteBookDto> getFavouriteBooks (@PathVariable ("userId") UUID userId) {
        return favouriteBookService.getFavouriteBooksByUserId(userId);
    }




}
