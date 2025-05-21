package ru.ifellow.jschool.ebredichina.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.service.BookInfoClientService;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book-infos")
public class BookInfoController {

    private final BookInfoClientService bookInfoClientService;


    @GetMapping("/filter")
    public List<BookInfoDto> findBook(@ModelAttribute @Validated BookFinderDto bookFinderDto) {
        return bookInfoClientService.findBook(bookFinderDto);
    }

    @GetMapping("/books-by-genre")
    public Map<String, List<BookInfoDto>> viewBooksByGenre() {
        return bookInfoClientService.viewBooksByGenre();
    }

    @GetMapping("/books-by-author")
    public List<BookInfoDto> viewBooksByAuthor(@RequestParam("author") @Validated @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$",
            message = "Имя автора должно содержать только буквы и/или пробелы") String author) {
        return bookInfoClientService.filterBooksByAuthor(author);
    }


}
