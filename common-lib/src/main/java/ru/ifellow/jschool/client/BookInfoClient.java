package ru.ifellow.jschool.client;

import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifellow.jschool.dto.BookFinderDto;
import ru.ifellow.jschool.dto.BookInfoDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "book-management-service", url = "${feign.book-management-service.url}")
public interface BookInfoClient {

    @GetMapping("api/v1/book-infos/filter")
    List<BookInfoDto> findBook (@ModelAttribute @Validated BookFinderDto bookFinderDto);

    @GetMapping("api/v1/book-infos/books-by-genre")
    Map<String, List<BookInfoDto>> groupBooksByGenre ();

    @GetMapping("api/v1/book-infos/books-by-author")
    List<BookInfoDto> getBooksByAuthor(@RequestParam("author") @Validated @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$",
            message = "Имя автора должно содержать только буквы и/или пробелы") String author);


    @GetMapping ("api/v1/book-infos/{id}")
    BookInfoDto  getBookById (@PathVariable("id") UUID id);
}
