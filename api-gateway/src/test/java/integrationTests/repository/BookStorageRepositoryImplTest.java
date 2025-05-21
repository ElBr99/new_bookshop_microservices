package integrationTests.repository;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.model.AbsStorage;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.repository.BookStorageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@IT
@RequiredArgsConstructor
@Sql(value = {"classpath:scripts/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/delete.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookStorageRepositoryImplTest {

    private final BookStorageRepository bookStorageRepository;


    @Test
    public void testFindById_ReturnsBookStorage() {

        UUID storageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        Optional<BookStorage> foundBookStorage = bookStorageRepository.findById(storageId);

        assertNotNull(foundBookStorage.get());
        assertNotNull(foundBookStorage.get());
        assertEquals(storageId, foundBookStorage.get().getId());
        assertEquals("123 Main St, Springfield", foundBookStorage.get().getAddress());

    }

    @Test
    public void testFindById_NonExistentStorage_ReturnsNull() {

        UUID nonExistingStorageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        Optional<BookStorage> bookStorage = bookStorageRepository.findById(nonExistingStorageId);

        assertTrue(bookStorage.isEmpty());

    }

    @Test
    public void testFindAllStorages_ReturnsListOfAllStorages() {

        List<String> expectedAddresses = List.of("123 Main St, Springfield", "456 Elm St, Metropolis");

        List<BookStorage> result = bookStorageRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().map(AbsStorage::getAddress).toList().containsAll(expectedAddresses));


    }

    @Test
    public void testIsEnoughBooks_ReturnsTrue_WhenEnoughBooksAvailable() {
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        int requiredAmount = 3;
        UUID storageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        boolean result = bookStorageRepository.isEnoughBooks(bookId, requiredAmount, storageId);

        assertTrue(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenNotEnoughBooksAvailable() {
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        int requiredAmount = 333;
        UUID storageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        boolean result = bookStorageRepository.isEnoughBooks(bookId, requiredAmount, storageId);
        assertFalse(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenWrongBookIdAndEnoughBooks() {

        UUID bookId = UUID.fromString("00000000-e29b-41d4-a716-446655440000");
        int requiredAmount = 3;
        UUID storageId = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        boolean result = bookStorageRepository.isEnoughBooks(bookId, requiredAmount, storageId);
        assertFalse(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenWrongBookIdAndNotEnoughBooks() {

        UUID bookId = UUID.fromString("00000000-e29b-41d4-a716-446655440000");
        int requiredAmount = 333;
        UUID storageId = UUID.fromString("91230e75-a625-453b-82eb-510acdcba719");

        boolean result = bookStorageRepository.isEnoughBooks(bookId, requiredAmount, storageId);
        assertFalse(result);
    }


    @Test
    public void testIsEnoughBooks_ReturnsNull_WhenStorageNotFound() {
        UUID bookId = UUID.fromString("a128f133-b2ee-4b99-9585-e47994048b5c");
        int requiredAmount = 333;
        UUID storageId = UUID.fromString("00000000-e29b-41d4-a716-446655440000");

        boolean result = bookStorageRepository.isEnoughBooks(bookId, requiredAmount, storageId);
        assertFalse(result);

    }


}
