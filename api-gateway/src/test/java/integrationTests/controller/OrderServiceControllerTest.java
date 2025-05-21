package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.dto.CreateOrderDto;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.jwt.JwtAuthFilter;
import ru.ifellow.jschool.ebredichina.mapper.ToSoldBooksMapper;
import ru.ifellow.jschool.ebredichina.model.SoldBook;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderServiceControllerTest {

    private final JwtAuthFilter jwtAuthFilter;
    private MockMvc mockMvc;
    @Autowired
    private ToSoldBooksMapper toSoldBooksMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(jwtAuthFilter).build();
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void registerOrderOnline_OK() throws Exception {
        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440012");

        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 2);
        List<BuyBookDto> bookDtoList = List.of(buyBookDto);
        CreateOrderDto createOrderDto = new CreateOrderDto(List.of(buyBookDto), BigDecimal.valueOf(17.0));
        String createOrderDtoJson = new ObjectMapper().writeValueAsString(createOrderDto);

        List<SoldBook> soldBooks = toSoldBooksMapper.mapToSoldBooks(bookDtoList);
        OnlineOrderDto onlineOrderDto = new OnlineOrderDto(customerId, soldBooks, OrderStatus.PENDING, createOrderDto.getMoneyFromCustomer());
        String onlineOrderDtoJson = new ObjectMapper().writeValueAsString(onlineOrderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderDtoJson)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW5lLnNtaXRoQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2NDM1ODQ5LCJleHAiOjE3Nzc5NzE4NDl9.jmTZYnZDhtwXQkEEhCMOPRSknUad8VKgnjG7PPmDQNUN-TvWhTAMj3Zr05SUQC8O")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(onlineOrderDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void acceptPurchaseOnline_OK() throws Exception {
        UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440012");
        UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655440014");
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 2);
        List<BuyBookDto> bookDtoList = List.of(buyBookDto);
        CreateOrderDto createOrderDto = new CreateOrderDto(List.of(buyBookDto), BigDecimal.valueOf(15.98));
        String createOrderDtoJson = new ObjectMapper().writeValueAsString(createOrderDto);

        List<SoldBook> soldBooks = toSoldBooksMapper.mapToSoldBooks(bookDtoList);

        CreateChequeDto createChequeDto = CreateChequeDto.builder()
                .orderId(orderId)
                .customerEmail("jane.smith@example.com")
                .customerBox(soldBooks)
                .sum(createOrderDto.getMoneyFromCustomer())
                .chequeType(ChequeType.ONLINE)
                .purchaseDate(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        String createChequeDtoJson = objectMapper.writeValueAsString(createChequeDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/online-purchase/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderDtoJson)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW5lLnNtaXRoQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2NDM1ODQ5LCJleHAiOjE3Nzc5NzE4NDl9.jmTZYnZDhtwXQkEEhCMOPRSknUad8VKgnjG7PPmDQNUN-TvWhTAMj3Zr05SUQC8O")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(createChequeDtoJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


}
