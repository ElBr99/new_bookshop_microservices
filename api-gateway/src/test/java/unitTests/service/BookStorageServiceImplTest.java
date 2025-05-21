package unitTests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.CreateBookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.exception.BookNotFoundException;
import ru.ifellow.jschool.ebredichina.exception.BookStorageNotFoundException;
import ru.ifellow.jschool.ebredichina.mapper.*;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.BookInfo;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.projections.BookStorageProjection;
import ru.ifellow.jschool.ebredichina.repository.BookStorageRepository;
import ru.ifellow.jschool.ebredichina.service.impl.BookStorageServiceImpl;
import ru.ifellow.jschool.ebredichina.service.impl.CommonStorageService;
import unitTests.BookStorageProjectionImpl;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookStorageServiceImplTest {

    @Mock
    private BookStorageRepository bookStorageRepository;


    @Spy
    private ToBookInfoMapper toBookInfoMapper = new ToBookInfoMapperImpl();

    @Spy
    private ToBookInfoDtoMapper toBookInfoDtoMapper = new ToBookInfoDtoMapperImpl();

    @Mock
    private BookStorageMapper bookStorageMapper;

    @Mock
    private CommonStorageService<BookStorage> bookStorageCommonStorageService;

    @InjectMocks
    private BookStorageServiceImpl bookStorageServiceImpl;

    @BeforeEach
    void before() {
        bookStorageServiceImpl = new BookStorageServiceImpl(
                new CommonStorageService<>(bookStorageRepository, () -> new BookNotFoundException("Такой склад не найден")),
                bookStorageRepository,
                toBookInfoMapper,
                toBookInfoDtoMapper,
                bookStorageMapper
        );
    }

    @Test
    void addBooksByPacks_OK() {

        BookStorage testingBookStorage = createTestingBookStorages().get(0);

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

        when(bookStorageRepository.findById(testingBookStorage.getId())).thenReturn(Optional.of(testingBookStorage));

        bookStorageServiceImpl.addBooksByPacks(testingBookStorage.getId(), createBookInfoDtoList);


        assertEquals(10, testingBookStorage.getBooks().get(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")).getAmount());
        assertEquals(31, testingBookStorage.getTotalAmount());
    }

    @Test
    void viewCurrentAssortmentTest_Ok() {
        BookStorage testingBookStorage = createTestingBookStorages().get(0);

        List<BookInfo> expectedList = createTestingBookStorages().get(0).getBooks().values().stream().toList();
        BookInfoDto bookInfoDtoOne = BookInfoDto.builder()
                .id(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"))
                .amount(5)
                .price(BigDecimal.valueOf(300))
                .build();

        BookInfoDto bookInfoDtoTwo = BookInfoDto.builder()
                .id(UUID.fromString("7b44e8f2-177d-4880-827e-941fa279546d"))
                .amount(3)
                .price(BigDecimal.valueOf(400))
                .build();

        List<BookInfoDto> expectedBookInfoDtos = List.of(bookInfoDtoOne, bookInfoDtoTwo);


        when(bookStorageRepository.findById(testingBookStorage.getId())).thenReturn(Optional.of(testingBookStorage));
        when(toBookInfoDtoMapper.listBookInfoToBookInfoDto(expectedList)).thenReturn(expectedBookInfoDtos);
        List<BookInfoDto> assortment = bookStorageServiceImpl.viewCurrentAssortment(testingBookStorage.getId());

        assertEquals(expectedBookInfoDtos, assortment);
    }

    @Test
    void selectsStorageForDeliveryTest_OK() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"), "Книга1", 3);
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"), "Книга3", 1);

        List<BookStorage> expectedStorages = createTestingBookStorages();
        List<BuyBookDto> customerList = List.of(buyBookDtoTwo, buyBookDto);
        List<UUID> bookInfoIds = List.of(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"),
                UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"));

        BookStorageProjectionImpl bookStorageProjection1 = BookStorageProjectionImpl.builder()
                .storageId("35ca5f62-d717-48e1-964f-09711c26f5e1")
                .bookInfoId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .bookId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .amount(5)
                .build();

        BookStorageProjectionImpl bookStorageProjection2 = BookStorageProjectionImpl.builder()
                .storageId("35ca5f62-d717-48e1-964f-09711c26f5e1")
                .bookInfoId("7b44e8f2-177d-4880-827e-941fa279546d")
                .bookId("7b44e8f2-177d-4880-827e-941fa279546d")
                .amount(3)
                .build();


        BookStorageProjectionImpl bookStorageProjection3 = BookStorageProjectionImpl.builder()
                .storageId("d04bb978-862a-498e-a071-8f09cf713c5c")
                .bookInfoId("631f306a-049d-4486-b69e-909b4b4ff984")
                .bookId("631f306a-049d-4486-b69e-909b4b4ff984")
                .amount(2)
                .build();

        BookStorageProjectionImpl bookStorageProjection4 = BookStorageProjectionImpl.builder()
                .storageId("d04bb978-862a-498e-a071-8f09cf713c5c")
                .bookInfoId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .bookId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .amount(5)
                .build();

        List<BookStorageProjection> bookStorageProjectionsList =
                List.of(bookStorageProjection1, bookStorageProjection3, bookStorageProjection4);


        when(bookStorageRepository.findAllStoragesWithBooks(bookInfoIds)).thenReturn(bookStorageProjectionsList);
        when(bookStorageRepository.findById(UUID.fromString("35ca5f62-d717-48e1-964f-09711c26f5e1"))).thenReturn(Optional.ofNullable(expectedStorages.get(0)));


        BookStorage selectedStorage = bookStorageServiceImpl.selectsStorageForDelivery(customerList);

        assertEquals(expectedStorages.get(0).getAddress(), selectedStorage.getAddress());
    }

    @Test
    void selectsStorageForDelivery_NoAvailableStorage() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"), "Книга1", 33);
        BuyBookDto buyBookDto = new BuyBookDto(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"), "Книга3", 33);


        List<BookStorage> expectedStorages = createTestingBookStorages();
        List<BuyBookDto> customerList = List.of(buyBookDtoTwo, buyBookDto);
        List<UUID> bookInfoIds = List.of(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"),
                UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"));

        BookStorageProjectionImpl bookStorageProjection1 = BookStorageProjectionImpl.builder()
                .storageId("35ca5f62-d717-48e1-964f-09711c26f5e1")
                .bookInfoId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .bookId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .amount(5)
                .build();

        BookStorageProjectionImpl bookStorageProjection2 = BookStorageProjectionImpl.builder()
                .storageId("35ca5f62-d717-48e1-964f-09711c26f5e1")
                .bookInfoId("7b44e8f2-177d-4880-827e-941fa279546d")
                .bookId("7b44e8f2-177d-4880-827e-941fa279546d")
                .amount(3)
                .build();


        BookStorageProjectionImpl bookStorageProjection3 = BookStorageProjectionImpl.builder()
                .storageId("d04bb978-862a-498e-a071-8f09cf713c5c")
                .bookInfoId("631f306a-049d-4486-b69e-909b4b4ff984")
                .bookId("631f306a-049d-4486-b69e-909b4b4ff984")
                .amount(2)
                .build();

        BookStorageProjectionImpl bookStorageProjection4 = BookStorageProjectionImpl.builder()
                .storageId("d04bb978-862a-498e-a071-8f09cf713c5c")
                .bookInfoId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .bookId("9c0ee823-3d15-4206-bb79-b1b2ce9031dc")
                .amount(5)
                .build();

        List<BookStorageProjection> bookStorageProjectionsList =
                List.of(bookStorageProjection1, bookStorageProjection3, bookStorageProjection4);

        when(bookStorageRepository.findAllStoragesWithBooks(bookInfoIds)).thenReturn(bookStorageProjectionsList);


        assertThrows(BookStorageNotFoundException.class, () -> {
            bookStorageServiceImpl.selectsStorageForDelivery(customerList);
        });
    }

    public List<BookStorage> createTestingBookStorages() {
        UUID storageOne = UUID.fromString("35ca5f62-d717-48e1-964f-09711c26f5e1");
        String address1 = "expectedBookStorage1";
        String name1 = "Склад3";
        BookInfo bookInfo1 = BookInfo.builder()
                .id(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"))
                .book(Book.builder()
                        .id(UUID.fromString("9c0ee823-3d15-4206-bb79-b1b2ce9031dc"))
                        .title("Книга1")
                        .author("Автор1")
                        .genre(BookGenre.STORY)
                        .publishingYear("1985")
                        .build())
                .amount(5)
                .price(BigDecimal.valueOf(300))
                .build();

        BookInfo bookInfo2 = BookInfo.builder()
                .id(UUID.fromString("7b44e8f2-177d-4880-827e-941fa279546d"))
                .book(Book.builder()
                        .id(UUID.fromString("7b44e8f2-177d-4880-827e-941fa279546d"))
                        .title("Книга2")
                        .author("Автор2")
                        .genre(BookGenre.NOVEL)
                        .publishingYear("1986")
                        .build())
                .amount(3)
                .price(BigDecimal.valueOf(400))
                .build();
        Map<UUID, BookInfo> books1 = new HashMap<>();
        books1.put(bookInfo1.getId(), bookInfo1);
        books1.put(bookInfo2.getId(), bookInfo2);

        UUID storageTwo = UUID.fromString("d04bb978-862a-498e-a071-8f09cf713c5c");
        String address2 = "expectedBookStorage2";
        String name2 = "Склад4";

        BookInfo bookInfo3 = BookInfo.builder()
                .id(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"))
                .book(Book.builder()
                        .id(UUID.fromString("631f306a-049d-4486-b69e-909b4b4ff984"))
                        .title("Книга3")
                        .author("Автор3")
                        .genre(BookGenre.STORY)
                        .build())
                .amount(2)
                .price(BigDecimal.valueOf(300))
                .build();

        Map<UUID, BookInfo> books2 = new HashMap<>();
        books2.put(bookInfo1.getId(), bookInfo1);
        books2.put(bookInfo3.getId(), bookInfo3);

        BookStorage bookStorage1 = new BookStorage(storageOne, address1, name1, bookInfo1.getAmount() + bookInfo2.getAmount());
        bookStorage1.addBook(bookInfo1);
        bookStorage1.addBook(bookInfo2);

        BookStorage bookStorage2 = new BookStorage(storageTwo, address2, name2, bookInfo1.getAmount() + bookInfo3.getAmount());
        bookStorage2.addBook(bookInfo1);
        bookStorage2.addBook(bookInfo3);

        return List.of(
                bookStorage1, bookStorage2
        );

    }
}
