package integrationTests.repository;

import integrationTests.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.ifellow.jschool.ebredichina.model.AbsStorage;
import ru.ifellow.jschool.ebredichina.model.OfflineBookShop;
import ru.ifellow.jschool.ebredichina.repository.OfflineBookShopRepository;

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
public class OfflineBookShopRepositoryImplTest {

    private final OfflineBookShopRepository offlineBookShopRepository;

    @Test
    public void findById_ReturnsShop() {
        UUID shopIdExisting = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        Optional<OfflineBookShop> foundShop = offlineBookShopRepository.findById(shopIdExisting);

        assertFalse(foundShop.isEmpty());
        assertEquals("789 Oak St, Gotham", foundShop.get().getAddress());
        assertEquals(shopIdExisting, foundShop.get().getId());
    }

    @Test
    public void findById_ReturnsNull() {
        UUID shopIdNonExisting = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        Optional<OfflineBookShop> foundShop = offlineBookShopRepository.findById(shopIdNonExisting);
        assertTrue(foundShop.isEmpty());

    }


    @Test
    public void testFindAllShops_ReturnsListOfAllShops() {

        List<String> expectedAddresses = List.of("789 Oak St, Gotham", "321 Pine St, Star City");

        List<OfflineBookShop> result = offlineBookShopRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().map(AbsStorage::getAddress).toList().containsAll(expectedAddresses));


    }

    @Test
    public void testIsEnoughBooks_ReturnsTrue_WhenEnoughBooksAvailable() {
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        int requiredAmount = 3;
        UUID shopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        boolean result = offlineBookShopRepository.isEnoughBooks(bookId, requiredAmount, shopId);

        assertTrue(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenNotEnoughBooksAvailable() {
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        int requiredAmount = 333;
        UUID shopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        boolean result = offlineBookShopRepository.isEnoughBooks(bookId, requiredAmount, shopId);
        assertFalse(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenWrongBookIdAndEnoughBooks() {

        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");
        int requiredAmount = 3;
        UUID shopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        boolean result = offlineBookShopRepository.isEnoughBooks(bookId, requiredAmount, shopId);
        assertFalse(result);
    }

    @Test
    public void testIsEnoughBooks_ReturnsFalse_WhenWrongBookIdAndNotEnoughBooks() {

        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");
        int requiredAmount = 333;
        UUID shopId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

        boolean result = offlineBookShopRepository.isEnoughBooks(bookId, requiredAmount, shopId);
        assertFalse(result);
    }


    @Test
    public void testIsEnoughBooks_ReturnsNull_WhenStorageNotFound() {
        UUID bookId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        int requiredAmount = 333;
        UUID shopId = UUID.fromString("00000000-e29b-41d4-a716-446655441111");

        boolean result = offlineBookShopRepository.isEnoughBooks(bookId, requiredAmount, shopId);
        assertFalse(result);

    }

}

