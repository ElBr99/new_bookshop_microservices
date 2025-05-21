package integrationTests.repository;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.dto.BookFinderDto;
import ru.ifellow.jschool.ebredichina.model.BookInfo;
import ru.ifellow.jschool.ebredichina.repository.BookInfoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class BookInfoRepositoryImplTest {

    private final BookInfoRepository bookInfoRepository;

    @Test
    public void testFindBookByAuthor_FullWordSearch_ReturnsOneBook() {
        BookFinderDto dto = new BookFinderDto(null, "Frank Herbert");
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getBook().getTitle());
    }


    @Test
    public void testFindBookByAuthor_ParticularWordSearch_ReturnsOneBook() {
        BookFinderDto dto = new BookFinderDto(null, "Fr");
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getBook().getTitle());
    }

    @Test
    public void testFindBookByTitle_FullWordSearch_ReturnsOneBook() {
        BookFinderDto dto = new BookFinderDto("Dune", null);
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getBook().getTitle());
    }

    @Test
    public void testFindBookByTitle_ParticularSearch() {
        BookFinderDto dto = new BookFinderDto("Du", null);
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getBook().getTitle());
    }

    @Test
    public void testFindBookByTitleAndAuthor_FullSearch() {
        BookFinderDto dto = new BookFinderDto("Dune", "Frank Herbert");
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getBook().getTitle());
    }

    @Test
    public void testFindBookNotFound() {
        BookFinderDto dto = new BookFinderDto("Нет такой книги", null);
        List<BookInfo> result = bookInfoRepository.findBookByAuthorOrTitle(dto.getBookTitle(), dto.getAuthor());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByAuthor_ReturnsListOfBooks() {

        String author = "Frank Herbert";
        List<BookInfo> result = bookInfoRepository.findByAuthor(author);

        assertEquals(2, result.size());

        assertEquals("Dune", result.get(0).getBook().getTitle());

        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindByAuthor_NoBooksFound() {
        String author = "нет такого автора";

        List<BookInfo> result = bookInfoRepository.findByAuthor(author);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllBooks_ReturnsAllBooks() {

        List<BookInfo> result = bookInfoRepository.findAll();

        assertFalse(result.isEmpty());

        assertEquals(3, result.size());

    }

}


