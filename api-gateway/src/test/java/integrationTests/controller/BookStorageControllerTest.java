package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.BookStorageDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.handler.ErrorResponse;
import ru.ifellow.jschool.ebredichina.jwt.JwtAuthFilter;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.BookStorage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookStorageControllerTest {

    private final JwtAuthFilter jwtAuthFilter;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(jwtAuthFilter).build();
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void viewCurrentAssortment_ExistingId_Ok() throws Exception {

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");

        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
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

        List<BookInfoDto> expectedAssortment = List.of(expectedBookInfoTwo);

        String expectedAssortmentJson = new ObjectMapper().writeValueAsString(expectedAssortment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/{bookStorageId}/assortment", bookStorageId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedAssortmentJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void viewCurrentAssortment_NonExistingId_ThrowsException() throws Exception {

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655441111");

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .message("Такой склад не найден")
                .build();

        String expectedMessageJson = new ObjectMapper().writeValueAsString(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/{bookStorageId}/assortment", bookStorageId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedMessageJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        System.out.println(Thread.currentThread().getName());

    }

    @Test
    void getStorage_ExistingId_Ok_PermittedAuthority() throws Exception {

        System.out.println(Thread.currentThread().getName());

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");
        Book expectedBookOne = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .title("Dune")
                .author("Frank Herbert")
                .genre(BookGenre.SCIENCE_FICTION)
                .publishingYear("1965")
                .build();

        BookStorageDto expectedBookStorageTwo = BookStorageDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .address("456 Elm St, Metropolis")
                .name("Metropolis Bookshop")
                .totalAmount(250)
                .books(null)
                .build();

        BookInfoDto expectedBookInfoTwo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440010"))
                .book(expectedBookOne)
                .amount(25)
                .price(BigDecimal.valueOf(8.50))
                .bookStorage(expectedBookStorageTwo.getId())
                .offlineBookShop(null)
                .build();
        Map<UUID, BookInfoDto> storageBooks = new HashMap<>();
        storageBooks.put(expectedBookInfoTwo.getId(), expectedBookInfoTwo);
        expectedBookStorageTwo.setBooks(storageBooks);


        String expectedStorageJson = new ObjectMapper().writeValueAsString(expectedBookStorageTwo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/{storageId}/storage", bookStorageId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJrZWVwZXIua2VlcGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDMxNjA3LCJleHAiOjE3Nzc1Njc2MDd9.uSERB-fhtqbn_qtPn3dPc9TQK_XGegLUhSlI-hwtt1ZJ-xzZF5mj_JmRIhs4SLsy")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedStorageJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void getStorage_ExistingId_Ok_NotPermittedAuthority_ThrowsAccessDenied() throws Exception {

        System.out.println(Thread.currentThread().getName());

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/{storageId}/storage", bookStorageId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTc0NjQzNTcxMSwiZXhwIjoxNzc3OTcxNzExfQ.HOdCuZ_q6E6ZSh61PWf-BQ16JCqGE1DpYesN_QFWdOE2F1rMupZWazpMrGyPfdbM"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Access Denied\"}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    void getStorage_NonExistingId_() throws Exception {

        System.out.println(Thread.currentThread().getName());

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655441111");

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .message("Такой склад не найден")
                .build();

        String expectedMessageJson = new ObjectMapper().writeValueAsString(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/{bookStorageId}/storage", bookStorageId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedMessageJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void getAllStorageIds_PermittedAuthority() throws Exception {
        List<UUID> expectedIds = List.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"), UUID.fromString("550e8400-e29b-41d4-a716-446655440005"));
        String expectedIdsJson = new ObjectMapper().writeValueAsString(expectedIds);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages")
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedIdsJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void getAllStorageIds_NotPermittedAuthority() throws Exception {
        System.out.println(Thread.currentThread().getName());
        List<UUID> expectedIds = List.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"), UUID.fromString("550e8400-e29b-41d4-a716-446655440005"));
        String expectedIdsJson = new ObjectMapper().writeValueAsString(expectedIds);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages")
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTc0NjQzNTcxMSwiZXhwIjoxNzc3OTcxNzExfQ.HOdCuZ_q6E6ZSh61PWf-BQ16JCqGE1DpYesN_QFWdOE2F1rMupZWazpMrGyPfdbM")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Access Denied\"}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    void getBookAmount_PermittedAuthority_Ok() throws Exception {

        System.out.println(Thread.currentThread().getName());
        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440010");

        int expectedAmount = 25;
        String expectedAmountJson = new ObjectMapper().writeValueAsString(expectedAmount);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/book-amount/{storageId}/{bookId}", bookStorageId, bookId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedAmountJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void getBookAmount_AuthenticationRequired_ThrowsException() throws Exception {

        System.out.println(Thread.currentThread().getName());

        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440010");

        ErrorResponse responseExceptionMessage = ErrorResponse.builder()
                .message("An Authentication object was not found in the SecurityContext")
                .build();

        String expectedResponse = new ObjectMapper().writeValueAsString(responseExceptionMessage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-storages/book-amount/{storageId}/{bookId}", bookStorageId, bookId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void addBooks() throws Exception {
        UUID bookStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");
        CreateBookInfoDto createBookInfoDtoOne = new CreateBookInfoDto("qwert", "qwer", BookGenre.NOVEL, 10,
                BigDecimal.valueOf(100), "1987");

        CreateBookInfoDto createBookInfoDtoTwo = new CreateBookInfoDto("zxc", "dfgg", BookGenre.SCIENCE_FICTION, 10,
                BigDecimal.valueOf(100), "1988");

        List<CreateBookInfoDto> createBookInfoDtoList = List.of(createBookInfoDtoOne, createBookInfoDtoTwo);
        String bookList = new ObjectMapper().writeValueAsString(createBookInfoDtoList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book-storages/{storageId}/books", bookStorageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookList)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }


}
