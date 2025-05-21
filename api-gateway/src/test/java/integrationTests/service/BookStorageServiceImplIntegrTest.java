package integrationTests.service;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.dto.BookStorageDto;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.exception.BookStorageNotFoundException;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.service.BookStorageService;
import ru.ifellow.jschool.ebredichina.service.StorageService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class BookStorageServiceImplIntegrTest {

    private final BookStorageService bookStorageService;
    private final StorageService<BookStorageDto> bookStorageServiceImpl;


    @Test
    void addBooksByPacks_OK() {

        BookStorageDto bookStorage = bookStorageServiceImpl.getStorageById(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"));

        CreateBookInfoDto createBookInfoDtoOne = CreateBookInfoDto.builder()
                .title("Книга 2")
                .author("Автор 2")
                .genre(BookGenre.NOVEL)
                .amount(10)
                .price(BigDecimal.valueOf(500))
                .publishingYear("1986")
                .build();

        CreateBookInfoDto createBookInfoDtoTwo = CreateBookInfoDto.builder()
                .title("Книга1")
                .author("Автор1")
                .genre(BookGenre.STORY)
                .amount(5)
                .price(BigDecimal.valueOf(400))
                .publishingYear("1985")
                .build();

        List<CreateBookInfoDto> createBookInfoDtoList = List.of(createBookInfoDtoOne, createBookInfoDtoTwo);

        bookStorageServiceImpl.addBooksByPacks(bookStorage.getId(), createBookInfoDtoList);

        BookStorageDto bookStorageAfterUpdate = bookStorageServiceImpl.getStorageById(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"));


        assertEquals(265, bookStorageAfterUpdate.getTotalAmount());


    }

    @Test
    void selectsStorageForDeliveryTest_OK() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 26);

        UUID expectedStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");
        List<BuyBookDto> customerList = List.of(buyBookDtoTwo);

        BookStorage selectedStorage = bookStorageService.selectsStorageForDelivery(customerList);
        assertEquals(expectedStorageId, selectedStorage.getId());
    }

    @Test
    void selectsStorageForDelivery_NoAvailableStorage() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Dune", 800);

        List<BuyBookDto> customerList = List.of(buyBookDtoTwo);

        assertThrows(BookStorageNotFoundException.class, () -> {
            bookStorageService.selectsStorageForDelivery(customerList);
        });
    }
}
