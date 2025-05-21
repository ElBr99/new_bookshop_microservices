package integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.annotation.IT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ifellow.jschool.ebredichina.client.UserClient;
import ru.ifellow.jschool.ebredichina.dto.CreateCustomerDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.UserRole;
import ru.ifellow.jschool.ebredichina.jwt.AuthRequest;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IT
@Sql(value = {"classpath:scripts/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:scripts/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserClient userClient;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getAuthToken_OK() throws Exception {
        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6")
                .userRole(UserRole.CUSTOMER)
                .build();


        when(userClient.getUserById(getUserDtoForReturn.getId())).thenReturn(getUserDtoForReturn);
        when(userClient.getUserByEmail(getUserDtoForReturn.getEmail())).thenReturn(getUserDtoForReturn);
        AuthRequest authRequest = new AuthRequest("jane.smith@example.com", "securepassword");
        String authRequestJson = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void getAuthToken_NonExistingEmail_Fails() throws Exception {
        AuthRequest authRequest = new AuthRequest("customer.customer@example.com", "sellerpassword");
        String authRequestJson = objectMapper.writeValueAsString(authRequest);

        GetUserDto getUserDtoForReturn = GetUserDto.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440012"))
                .email("jane.smith@example.com")
                .name("Jane Smith")
                .password("$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6")
                .userRole(UserRole.CUSTOMER)
                .build();

        String expectedRequestJson = objectMapper.writeValueAsString("{\"message\":\"Bad credentials\"}");


        when(userClient.getUserByEmail(authRequest.getUsername())).thenThrow(UsernameNotFoundException.class);
        //  when(userClient.getUserByEmail(authRequest.getUsername())).;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Bad credentials\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void registerCustomer_OK() throws Exception {

        CreateCustomerDto createCustomerDto = new CreateCustomerDto("customer1.customer@mail.ru", "Customer1@", "custCust");
        String createCustomerJson = objectMapper.writeValueAsString(createCustomerDto);

        GetUserDto expectedUser = GetUserDto.builder()
                .email(createCustomerDto.getEmail())
                .password(createCustomerDto.getPassword())
                .name(createCustomerDto.getName())
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCustomerJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(userClient, times(1)).createCustomer(any());


    }

    @Test
    void registerCustomer_NonValidPassword() throws Exception {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("customer1.customer@mail.ru", "customer", "custCust");
        String createCustomerJson = objectMapper.writeValueAsString(createCustomerDto);

        doThrow(new RuntimeException()).when(userClient).createCustomer(createCustomerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCustomerJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Validation failed\",\"errors\":{\"password\":[\"Пароль должен содержать как минимум одну цифру, одну строчную букву, одну заглавную букву и один из символов: @, #, $, %.\"]}}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //verify(userClient, times(1)).createCustomer(any());
    }

    @Test
    void registerCustomer_NonValidEmail() throws Exception {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("customer1.customer", "Customer1@", "custCust");
        String createCustomerJson = objectMapper.writeValueAsString(createCustomerDto);

        doThrow(new RuntimeException()).when(userClient).createCustomer(createCustomerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCustomerJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Validation failed\",\"errors\":{\"email\":[\"Email должен быть корректным и содержать символ '@'.Например:customer@mail.ru\"]}}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


}
