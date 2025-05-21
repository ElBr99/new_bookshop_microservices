package ru.ifellow.ebredichina.bookservice.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.bookservice.service.BookInfoService;
import ru.ifellow.jschool.dto.BookFinderDto;
import ru.ifellow.jschool.dto.BookInfoDto;
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
    public BookInfoDto getBookById(@PathVariable("id") UUID id) {
        return bookInfoService.getBookInfo(id);
    }

}
