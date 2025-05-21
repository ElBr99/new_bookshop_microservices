package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.BookStorage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IT
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookInfoControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void findBookTestFullWordSearch_ValidData_Ok_EnteredAsCustomer() throws Exception {

        BookFinderDto bookFinderDto = new BookFinderDto("Dune", "Frank Herbert");
        String bookTitle = bookFinderDto.getBookTitle();
        String author = bookFinderDto.getAuthor();

        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage expectedBookStorageOne = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .address("123 Main St, Springfield")
                .name("Springfield Bookstore")
                .totalAmount(100)
                .build();

        BookInfoDto expectedBookInfoOne = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"))
                .book(expectedBookOne)
                .amount(30)
                .price(BigDecimal.valueOf(9.99))
                .bookStorage(expectedBookStorageOne.getId())
                .offlineBookShop(null)
                .build();

        BookStorage expectedBookStorageTwo = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .address("456 Elm St, Metropolis")
                .name("Metropolis Bookshop")
                .totalAmount(250)
                .build();

        BookInfoDto expectedBookInfoTwo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440010"))
                .book(expectedBookOne)
                .amount(25)
                .price(BigDecimal.valueOf(8.50))
                .bookStorage(expectedBookStorageTwo.getId())
                .offlineBookShop(null)
                .build();

        List<BookInfoDto> expectedBookInfoDto = List.of(expectedBookInfoOne, expectedBookInfoTwo);

        String expectedBooksJson = new ObjectMapper().writeValueAsString(expectedBookInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/filter")
                        .param("bookTitle", bookTitle)
                        .param("author", author)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTc0NjQzNTcxMSwiZXhwIjoxNzc3OTcxNzExfQ.HOdCuZ_q6E6ZSh61PWf-BQ16JCqGE1DpYesN_QFWdOE2F1rMupZWazpMrGyPfdbM")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBooksJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void findBookTestFullWordSearch_ValidData_Ok_EnteredAsSeller() throws Exception {

        BookFinderDto bookFinderDto = new BookFinderDto("Dune", "Frank Herbert");
        String bookTitle = bookFinderDto.getBookTitle();
        String author = bookFinderDto.getAuthor();

        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage expectedBookStorageOne = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .address("123 Main St, Springfield")
                .name("Springfield Bookstore")
                .totalAmount(100)
                .build();

        BookInfoDto expectedBookInfoOne = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"))
                .book(expectedBookOne)
                .amount(30)
                .price(BigDecimal.valueOf(9.99))
                .bookStorage(expectedBookStorageOne.getId())
                .offlineBookShop(null)
                .build();

        BookStorage expectedBookStorageTwo = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .address("456 Elm St, Metropolis")
                .name("Metropolis Bookshop")
                .totalAmount(250)
                .build();

        BookInfoDto expectedBookInfoTwo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440010"))
                .book(expectedBookOne)
                .amount(25)
                .price(BigDecimal.valueOf(8.50))
                .bookStorage(expectedBookStorageTwo.getId())
                .offlineBookShop(null)
                .build();

        List<BookInfoDto> expectedBookInfoDto = List.of(expectedBookInfoOne, expectedBookInfoTwo);

        String expectedBooksJson = new ObjectMapper().writeValueAsString(expectedBookInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/filter")
                        .param("bookTitle", bookTitle)
                        .param("author", author)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBooksJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void findBookTestFullWordSearch_NotValidAuthorName_ThrowsException() throws Exception {

        BookFinderDto bookFinderDto = new BookFinderDto("Dune", "1");
        String bookTitle = bookFinderDto.getBookTitle();
        String author = bookFinderDto.getAuthor();


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/filter")
                        .param("bookTitle", bookTitle)
                        .param("author", author))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"message\":\"Validation failed\",\"errors\":{\"author\":[\"Имя автора должно содержать только буквы и пробелы\",\"Имя автора должно быть более 3-х символов, но менее 100\"]}}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void findBookTestFullWordSearch_NotValidDataTitle_ThrowsException() throws Exception {

        BookFinderDto bookFinderDto = new BookFinderDto("D", "Frank");
        String bookTitle = bookFinderDto.getBookTitle();
        String author = bookFinderDto.getAuthor();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/filter")
                        .param("bookTitle", bookTitle)
                        .param("author", author))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Validation failed\",\"errors\":{\"bookTitle\": [\"Название книги должно быть более 3-х символов, но менее 200\"]}}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void viewBooksByAuthor_Valid_Ok() throws Exception {

        String author = "Frank Herbert";
        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage expectedBookStorageOne = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .address("123 Main St, Springfield")
                .name("Springfield Bookstore")
                .totalAmount(100)
                .build();

        BookInfoDto expectedBookInfoOne = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"))
                .book(expectedBookOne)
                .amount(30)
                .price(BigDecimal.valueOf(9.99))
                .bookStorage(expectedBookStorageOne.getId())
                .offlineBookShop(null)
                .build();

        BookStorage expectedBookStorageTwo = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .address("456 Elm St, Metropolis")
                .name("Metropolis Bookshop")
                .totalAmount(250)
                .build();

        BookInfoDto expectedBookInfoTwo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440010"))
                .book(expectedBookOne)
                .amount(25)
                .price(BigDecimal.valueOf(8.50))
                .bookStorage(expectedBookStorageTwo.getId())
                .offlineBookShop(null)
                .build();

        List<BookInfoDto> expectedBookInfoDto = List.of(expectedBookInfoOne, expectedBookInfoTwo);
        String expectedBooksJson = new ObjectMapper().writeValueAsString(expectedBookInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/books-by-author")
                        .param("author", author))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBooksJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void viewBooksByAuthor_Valid_Ok_EnteredAsBookKeeper() throws Exception {

        String author = "Frank Herbert";
        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();
        BookStorage expectedBookStorageOne = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .address("123 Main St, Springfield")
                .name("Springfield Bookstore")
                .totalAmount(100)
                .build();

        BookInfoDto expectedBookInfoOne = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"))
                .book(expectedBookOne)
                .amount(30)
                .price(BigDecimal.valueOf(9.99))
                .bookStorage(expectedBookStorageOne.getId())
                .offlineBookShop(null)
                .build();

        BookStorage expectedBookStorageTwo = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .address("456 Elm St, Metropolis")
                .name("Metropolis Bookshop")
                .totalAmount(250)
                .build();

        BookInfoDto expectedBookInfoTwo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440010"))
                .book(expectedBookOne)
                .amount(25)
                .price(BigDecimal.valueOf(8.50))
                .bookStorage(expectedBookStorageTwo.getId())
                .offlineBookShop(null)
                .build();

        List<BookInfoDto> expectedBookInfoDto = List.of(expectedBookInfoOne, expectedBookInfoTwo);
        String expectedBooksJson = new ObjectMapper().writeValueAsString(expectedBookInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-infos/books-by-author")
                        .param("author", author)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJrZWVwZXIua2VlcGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDMxNjA3LCJleHAiOjE3Nzc1Njc2MDd9.uSERB-fhtqbn_qtPn3dPc9TQK_XGegLUhSlI-hwtt1ZJ-xzZF5mj_JmRIhs4SLsy")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBooksJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }


}
