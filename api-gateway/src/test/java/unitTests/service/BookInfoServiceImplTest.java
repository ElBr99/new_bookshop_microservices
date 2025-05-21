package unitTests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.dto.BookInfoDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.mapper.ToBookInfoDtoMapper;
import ru.ifellow.jschool.ebredichina.model.Book;
import ru.ifellow.jschool.ebredichina.model.BookInfo;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.repository.BookInfoRepository;
import ru.ifellow.jschool.ebredichina.service.impl.BookInfoClientServiceImpl;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookInfoServiceImplTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @Spy
    private ToBookInfoDtoMapper toBookInfoDtoMapper;

    @InjectMocks
    private BookInfoClientServiceImpl bookInfoServiceImpl;


    @Test
    void findBookTest_byTitle_OK() {

        BookFinderDto dto = new BookFinderDto("Dune", null);
        Book book = new Book(UUID.fromString("550e8400-e29b-41d4-a716-446655440014"), "Dune", "Frank Herbert", BookGenre.NOVEL, "1999");
        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();
        BookInfo expectedBookInfo = new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 20, BigDecimal.valueOf(100), b, null);
        List<BookInfo> bookInfos = List.of(expectedBookInfo);

        List<BookInfoDto> expectedBookInfoDto = List.of(new BookInfoDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 20, BigDecimal.valueOf(100), b.getId(), null));


        when(bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor())).thenReturn(bookInfos);
        when(toBookInfoDtoMapper.listBookInfoToBookInfoDto(bookInfos)).thenReturn(expectedBookInfoDto);

        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(dto);

        assertNotNull(foundList);
        assertEquals(1, foundList.size());
        assertEquals(foundList.get(0).getBook(), expectedBookInfoDto.get(0).getBook());
        verify(bookInfoRepository, times(1)).findBookByAuthorOrTitle(any(), any());
    }

    @Test
    void findBookTest_byAuthor_OK() {

        BookFinderDto dto = new BookFinderDto(null, "Frank Herbert");
        Book book = new Book(UUID.fromString("550e8400-e29b-41d4-a716-446655440014"), "Dune", "Frank Herbert", BookGenre.NOVEL, "1999");
        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();
        BookInfo expectedBookInfo = new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 20, BigDecimal.valueOf(100), b, null);
        List<BookInfo> bookInfos = List.of(expectedBookInfo);

        List<BookInfoDto> expectedBookInfoDto = List.of(new BookInfoDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book, 20, BigDecimal.valueOf(100), b.getId(), null));

        when(bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor())).thenReturn(bookInfos);
        when(toBookInfoDtoMapper.listBookInfoToBookInfoDto(bookInfos)).thenReturn(expectedBookInfoDto);


        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(dto);


        assertNotNull(foundList);
        assertEquals(1, foundList.size());
        assertEquals(expectedBookInfoDto, foundList);
        verify(bookInfoRepository, times(1)).findBookByAuthorOrTitle(any(), any());
    }

    @Test
    void findBookTest_EmptyList() {

        BookFinderDto bookFinderDto = new BookFinderDto("нет такого автора", null);
        List<BookInfo> expectedBooks = new ArrayList<>();

        when(bookInfoRepository.findBookByAuthorOrTitle(bookFinderDto.getBookTitle(), bookFinderDto.getAuthor())).thenReturn(expectedBooks);

        List<BookInfoDto> foundList = bookInfoServiceImpl.findBook(bookFinderDto);

        assertNotNull(foundList);
        assertTrue(foundList.isEmpty());
        verify(bookInfoRepository, times(1)).findBookByAuthorOrTitle(any(), any());
    }

    @Test
    void viewBooksByGenreTest_OK() {

        Map<String, List<BookInfo>> expectedBooksByGenre = new HashMap<>();
        Book book1 = new Book(UUID.fromString("550e8400-e29b-41d4-a716-446655440014"), "Dune", "Frank Herbert", BookGenre.NOVEL, "1999");
        Book book2 = new Book(UUID.fromString("550e8400-e29b-41d4-a716-446655440015"), "Du", "Frank Bert", BookGenre.SCIENCE_FICTION, "1999");

        BookStorage b = BookStorage.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .build();

        BookInfo expectedBookInfo1 = new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"), book1, 20, BigDecimal.valueOf(100), b, null);
        BookInfo expectedBookInfo2 = new BookInfo(UUID.fromString("550e8400-e29b-41d4-a716-446655440009"), book2, 20, BigDecimal.valueOf(100), b, null);
        List<BookInfo> bookInfos = List.of(expectedBookInfo1, expectedBookInfo2);

        expectedBooksByGenre.put(book1.getGenre().name(), List.of(expectedBookInfo1));
        expectedBooksByGenre.put(book2.getGenre().name(), List.of(expectedBookInfo2));

        when(bookInfoRepository.findAll()).thenReturn(bookInfos);
        Map<String, List<BookInfoDto>> foundBooks = bookInfoServiceImpl.viewBooksByGenre();

        assertNotNull(foundBooks);
        verify(bookInfoRepository, times(1)).findAll();
    }

    @Test
    void viewBooksByGenreTest_EmptyMap() {

        Map<String, List<BookInfo>> expectedBooksByGenre = new HashMap<>();
        List<BookInfo> emptyList = new ArrayList<>();

        when(bookInfoRepository.findAll()).thenReturn(emptyList);

        Map<String, List<BookInfoDto>> result = bookInfoServiceImpl.viewBooksByGenre();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookInfoRepository, times(1)).findAll();
    }

    @Test
    void filterBooksByAuthorTest_OK() {

        String author = "Олдос Хаксли";

        List<BookInfo> expectedBooks = fillTestingData().values().stream().flatMap(Collection::stream)
                .filter(bookInfo -> bookInfo.getBook().getAuthor().equals("Олдос Хаксли")).toList();
        BookInfoDto bookInfoDtoOne = BookInfoDto.builder()
                .book(Book.builder()
                        .title("О дивный новый мир")
                        .author("Олдос Хаксли")
                        .build())
                .build();

        BookInfoDto bookInfoDtoTwo = BookInfoDto.builder()
                .book(Book.builder()
                        .title("Возвращение в дивный новый мир")
                        .author("Олдос Хаксли")
                        .build())
                .build();

        List<BookInfoDto> expectedBookInfoDtos = List.of(bookInfoDtoOne, bookInfoDtoTwo);

        when(bookInfoRepository.findByAuthor(author)).thenReturn(expectedBooks);
        when(toBookInfoDtoMapper.listBookInfoToBookInfoDto(expectedBooks)).thenReturn(expectedBookInfoDtos);

        List<BookInfoDto> foundBooks = bookInfoServiceImpl.filterBooksByAuthor(author);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        assertEquals(expectedBookInfoDtos, foundBooks);
        verify(bookInfoRepository, times(1)).findByAuthor(author);
    }

    @Test
    void filterBooksByAuthorTest_Empty() {

        String author = "НЕт такого автора";
        List<BookInfo> expectedBooks = List.of();

        when(bookInfoRepository.findByAuthor(author)).thenReturn(expectedBooks);

        List<BookInfoDto> foundBooks = bookInfoServiceImpl.filterBooksByAuthor(author);

        assertNotNull(foundBooks);
        assertTrue(foundBooks.isEmpty());
        verify(bookInfoRepository, times(1)).findByAuthor(author);
    }

    public Map<String, List<BookInfo>> fillTestingData() {

        BookInfo bookInfo1 = BookInfo.builder()
                .book(Book.builder()
                        .author("Олдос Хаксли")
                        .title("О дивный новый мир")
                        .build())
                .build();

        BookInfo bookInfo2 = BookInfo.builder()
                .book(Book.builder()
                        .author("Олдос Хаксли")
                        .title("Возвращение в дивный новый мир")
                        .build())
                .build();

        BookInfo bookInfo3 = BookInfo.builder()
                .book(Book.builder()
                        .author("Фёдор Достоевский")
                        .title("Преступление и наказание")
                        .build())
                .build();

        Map<String, List<BookInfo>> expectedBooksByGenre = new HashMap<>();
        expectedBooksByGenre.put(BookGenre.SCIENCE_FICTION.name(), List.of(bookInfo1, bookInfo2));
        expectedBooksByGenre.put(BookGenre.NOVEL.name(), List.of(bookInfo3));

        return expectedBooksByGenre;

    }
}


