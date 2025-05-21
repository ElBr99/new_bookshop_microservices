package integrationTests.service;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.BookInfo;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.repository.BookInfoRepository;
import ru.ifellow.jschool.ebredichina.service.BookInfoClientService;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookInfoServiceImplIntegrTest {

    private final BookInfoClientService bookInfoServiceImpl;
    private final BookInfoRepository bookInfoRepository;

    @Test
    void findBookTest_byTitle_OK() {

        BookFinderDto dto = new BookFinderDto("Dune", null);

        Book book = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();

        List<BookInfo> expectedBooks =
                List.of(new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 30, BigDecimal.valueOf(9.99), b, null));


        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(dto);

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertEquals(expectedBooks.get(0).getBook().getId(), foundList.get(0).getBook().getId());

    }

    @Test
    void findBookTest_byAuthor_OK() {

        BookFinderDto dto = new BookFinderDto(null, "Frank Herbert");

        Book book = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();

        List<BookInfo> expectedBooks =
                List.of(new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 30, BigDecimal.valueOf(9.99), b, null));


        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(dto);


        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertEquals(expectedBooks.get(0).getBook().getId(), foundList.get(0).getBook().getId());

    }

    @Test
    void findBookTest_ReturnsAll() {

        BookFinderDto bookFinderDto = new BookFinderDto(null, null);
        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(bookFinderDto);

        assertEquals(3, foundList.size());

    }

    @Test
    void viewBooksByGenreTest_OK() {

        Set<String> keys = new HashSet<>();
        keys.add("NOVEL");
        keys.add("SCIENCE_FICTION");


        Map<String, List<BookInfoDto>> foundBooks = bookInfoServiceImpl.viewBooksByGenre();

        assertNotNull(foundBooks);
        assertTrue(foundBooks.containsKey("NOVEL"));
        assertEquals(keys, foundBooks.keySet());
    }


    @Test
    void filterBooksByAuthorTest_OK() {

        String author = "Frank Herbert";

        Book book = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();

        List<BookInfo> expectedBooks =
                List.of(new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 30, BigDecimal.valueOf(9.99), b, null));

        List<BookInfoDto> foundBooks = bookInfoServiceImpl.filterBooksByAuthor(author);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        assertEquals(expectedBooks.get(0).getBook().getAuthor(), foundBooks.get(0).getBook().getAuthor());

    }

    @Test
    void filterBooksByAuthorTest_Empty() {

        String author = "НЕт такого автора";
        List<BookInfo> expectedBooks = List.of();

        List<BookInfoDto> foundBooks = bookInfoServiceImpl.filterBooksByAuthor(author);

        assertNotNull(foundBooks);
        assertTrue(foundBooks.isEmpty());
        assertEquals(expectedBooks, foundBooks);

    }


}
