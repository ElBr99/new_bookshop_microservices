package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.ifellow.jschool.ebredichina.client.UserClient;
import ru.ifellow.jschool.ebredichina.dto.EmitterMessage;
import ru.ifellow.jschool.ebredichina.dto.SendBookInfo;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.UserRole;
import ru.ifellow.jschool.ebredichina.jwt.JwtAuthFilter;
import ru.ifellow.jschool.ebredichina.mapper.BookMapper;
import ru.ifellow.jschool.ebredichina.model.FavouriteBook;
import ru.ifellow.jschool.ebredichina.repository.FavouriteBookRepository;
import ru.ifellow.jschool.ebredichina.sheduler.EmitterScheduler;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FavouriteBookControllerTest {

    private final JwtAuthFilter jwtAuthFilter;
    private final EmitterScheduler emitterScheduler;
    private final BookMapper bookMapper;
    private final FavouriteBookRepository favouriteBookRepository;
    private final ObjectMapper objectMapper;
    private final Map<UUID, SseEmitter> subscribers;

    @MockitoBean
    private UserClient userClient;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        subscribers.clear();
    }

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(jwtAuthFilter).build();
    }

    @AfterEach
    void clearSecurityContext() {

        SecurityContextHolder.clearContext();
    }

    @Test
    void addBooksToFavourites() throws Exception {
        UUID bookInfoId = UUID.fromString("550e8400-e29b-41d4-a716-446655440008");

        String bookInfoIdJson = new ObjectMapper().writeValueAsString(bookInfoId);
        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6")
                .userRole(UserRole.CUSTOMER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favourites")
                        .param("bookInfoId", bookInfoId.toString())
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW5lLnNtaXRoQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2NDM1ODQ5LCJleHAiOjE3Nzc5NzE4NDl9.jmTZYnZDhtwXQkEEhCMOPRSknUad8VKgnjG7PPmDQNUN-TvWhTAMj3Zr05SUQC8O")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void subscribeCustomerScheduleEmitter() throws Exception {

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6")
                .userRole(UserRole.CUSTOMER)
                .build();

        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favourites/subscribes")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW5lLnNtaXRoQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQ2NDM1ODQ5LCJleHAiOjE3Nzc5NzE4NDl9.jmTZYnZDhtwXQkEEhCMOPRSknUad8VKgnjG7PPmDQNUN-TvWhTAMj3Zr05SUQC8O")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();


        SseEmitter createdEmitter = subscribers.get(getUserDtoForReturn.getId());
        Assertions.assertNotNull(createdEmitter);
        Assertions.assertTrue(subscribers.containsKey(getUserDtoForReturn.getId()));

        emitterScheduler.schedule();

        List<FavouriteBook> booksFromDb = favouriteBookRepository.findFavouriteBooksByUserId(getUserDtoForReturn.getId());

        List<SendBookInfo> expectedSendBookInfoList = bookMapper.toSendBookInfoList(booksFromDb);
        String expectedEventType = "Напоминание о книгах, добавленных в избранное";
        UUID userId = getUserDtoForReturn.getId();

        EmitterMessage expectedEmitterMessage = new EmitterMessage(userId.toString(), expectedEventType, expectedSendBookInfoList);

        createdEmitter.complete();

        Thread.sleep(5000);

        MvcResult mvcResult1 = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(result -> {
                    content().contentType(MediaType.APPLICATION_JSON);
                    String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    System.out.println("Received SSE content:\n" + content);
                }).andReturn();

        MockHttpServletResponse response = mvcResult1.getResponse();

        String content1 = response.getContentAsString(StandardCharsets.UTF_8);
        String content2 = content1.substring(5, content1.length());

        EmitterMessage foundMessage = objectMapper.readValue(content2, EmitterMessage.class);

        Assertions.assertEquals(expectedEmitterMessage.getMessage(), foundMessage.getMessage());
        Assertions.assertEquals(expectedEmitterMessage.getFavouriteBooks(), foundMessage.getFavouriteBooks());
        Assertions.assertEquals(expectedEmitterMessage.getUserId(), foundMessage.getUserId());
    }

}



