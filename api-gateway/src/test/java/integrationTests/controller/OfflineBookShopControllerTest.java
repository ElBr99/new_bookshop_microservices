package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ifellow.jschool.ebredichina.client.UserClient;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.OfflineBookShopDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.enums.UserRole;
import ru.ifellow.jschool.ebredichina.handler.ErrorResponse;
import ru.ifellow.jschool.ebredichina.jwt.JwtAuthFilter;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.OfflineBookShop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OfflineBookShopControllerTest {

    private final JwtAuthFilter jwtAuthFilter;
    private MockMvc mockMvc;

    @MockitoBean
    private UserClient userClient;

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

        UUID bookShopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        Book expectedBook = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))
                .title("1984")
                .author("George Orwell")
                .genre(BookGenre.NOVEL)
                .publishingYear("1949")
                .build();

        OfflineBookShop expectedBookShop = OfflineBookShop.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440006"))
                .address("789 Oak St, Gotham")
                .name("Gotham Books")
                .totalAmount(150)
                .build();

        BookInfoDto expectedBookInfo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440009"))
                .book(expectedBook)
                .amount(20)
                .price(BigDecimal.valueOf(7.99))
                .bookStorage(null)
                .offlineBookShop(expectedBookShop.getId())
                .build();

        List<BookInfoDto> expectedAssortment = List.of(expectedBookInfo);

        String expectedAssortmentJson = new ObjectMapper().writeValueAsString(expectedAssortment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-shops/{shopId}/assortment", bookShopId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedAssortmentJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void viewCurrentAssortment_NonExistingId_ThrowsException() throws Exception {

        UUID shopId = UUID.fromString("550e8400-e29b-41d4-a716-446655441111");

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .message("Такой магазин не найден")
                .build();

        String expectedMessageJson = new ObjectMapper().writeValueAsString(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-shops/{shopId}/assortment", shopId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedMessageJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void getShop_ExistingId_Ok() throws Exception {

        UUID bookShopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        Book expectedBook = Book.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))
                .title("1984")
                .author("George Orwell")
                .genre(BookGenre.NOVEL)
                .publishingYear("1949")
                .build();

        OfflineBookShopDto expectedBookShop = OfflineBookShopDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440006"))
                .address("789 Oak St, Gotham")
                .name("Gotham Books")
                .totalAmount(150)
                .build();

        BookInfoDto expectedBookInfo = BookInfoDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440009"))
                .book(expectedBook)
                .amount(20)
                .price(BigDecimal.valueOf(7.99))
                .bookStorage(null)
                .offlineBookShop(expectedBookShop.getId())
                .build();

        Map<UUID, BookInfoDto> shopBooks = new HashMap<>();
        shopBooks.put(expectedBookInfo.getId(), expectedBookInfo);
        expectedBookShop.setBooks(shopBooks);


        String expectedStorageJson = new ObjectMapper().writeValueAsString(expectedBookShop);

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("be610de3-0b62-4de8-9530-4164289839b6"))
                .email("seller.seller@example.com")
                .name("Seller Seller")
                .password("$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO")
                .userRole(UserRole.BOOKSELLER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-shops/{shopId}/shop", bookShopId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedStorageJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void getAllShopsIds() throws Exception {
        List<UUID> expectedIds = List.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440006"), UUID.fromString("550e8400-e29b-41d4-a716-446655440007"));
        String expectedIdsJson = new ObjectMapper().writeValueAsString(expectedIds);

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("be610de3-0b62-4de8-9530-4164289839b6"))
                .email("seller.seller@example.com")
                .name("Seller Seller")
                .password("$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO")
                .userRole(UserRole.BOOKSELLER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-shops")
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedIdsJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void getBookAmount() throws Exception {

        UUID bookShopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440009");

        int expectedAmount = 20;
        String expectedAmountJson = new ObjectMapper().writeValueAsString(expectedAmount);

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("be610de3-0b62-4de8-9530-4164289839b6"))
                .email("seller.seller@example.com")
                .name("Seller Seller")
                .password("$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO")
                .userRole(UserRole.BOOKSELLER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book-shops/book-amount/{shopId}/{bookId}", bookShopId, bookId)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedAmountJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void addBooks() throws Exception {
        UUID bookShopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");
        CreateBookInfoDto createBookInfoDtoOne = new CreateBookInfoDto("qwert", "qwer", BookGenre.NOVEL, 10,
                BigDecimal.valueOf(100), "1987");

        CreateBookInfoDto createBookInfoDtoTwo = new CreateBookInfoDto("zxc", "dfgg", BookGenre.SCIENCE_FICTION, 10,
                BigDecimal.valueOf(100), "1988");

        List<CreateBookInfoDto> createBookInfoDtoList = List.of(createBookInfoDtoOne, createBookInfoDtoTwo);
        String bookList = new ObjectMapper().writeValueAsString(createBookInfoDtoList);

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("be610de3-0b62-4de8-9530-4164289839b6"))
                .email("seller.seller@example.com")
                .name("Seller Seller")
                .password("$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO")
                .userRole(UserRole.BOOKSELLER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book-shops/{shopId}/books", bookShopId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookList)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzZWxsZXIuc2VsbGVyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2MDI4NDMyLCJleHAiOjE3Nzc1NjQ0MzJ9.XdJXyZitKHJtkCttHeZiPwEZFYYqRjp7S248SgAitdPpH6DKPjNvOtOhJGCoeOla"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
