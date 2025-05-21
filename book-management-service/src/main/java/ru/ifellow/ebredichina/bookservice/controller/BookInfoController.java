package ru.ifellow.ebredichina.bookservice.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.ebredichina.bookservice.dto.BookFinderDto;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.service.BookInfoService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-infos")
public class BookInfoController {

    private final BookInfoService bookInfoService;


    @GetMapping("/filter")
    public List<BookInfoDto> getBook(@ModelAttribute @Validated BookFinderDto bookFinderDto) {
        return bookInfoService.findBook(bookFinderDto);
    }

    @GetMapping("/books-by-genre")
    public Map<String, List<BookInfoDto>> getBooksByGenre() {
        return bookInfoService.viewBooksByGenre();
    }

    @GetMapping("/books-by-author")
    public List<BookInfoDto> getBooksByAuthor(@RequestParam("author") @Validated @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$",
            message = "Имя автора должно содержать только буквы и/или пробелы") String author) {
        return bookInfoService.filterBooksByAuthor(author);
    }

    @GetMapping("/{id}")
    public BookInfoDto  getBookById (@PathVariable ("id")UUID id) {
        return bookInfoService.getBookInfo(id);
    }

}
