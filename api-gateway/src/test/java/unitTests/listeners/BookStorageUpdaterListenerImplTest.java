package unitTests.listeners;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifellow.jschool.ebredichina.dto.BuyBookDto;
import ru.ifellow.jschool.ebredichina.dto.userClientDto.GetUserDto;
import ru.ifellow.jschool.ebredichina.enums.BookGenre;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.enums.OrderStatus;
import ru.ifellow.jschool.ebredichina.listener.BookStorageUpdaterListener;
import ru.ifellow.jschool.ebredichina.model.*;
import ru.ifellow.jschool.ebredichina.service.impl.CommonStorageService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class BookStorageUpdaterListenerImplTest {

    private final CommonStorageService<OfflineBookShop> offlineBookShopService;

    private final CommonStorageService<BookStorage> bookStorageService;

    private final BookStorageUpdaterListener bookStorageUpdaterListener;

    {
        offlineBookShopService = Mockito.mock(CommonStorageService.class);
        bookStorageService = Mockito.mock(CommonStorageService.class);
        bookStorageUpdaterListener = new BookStorageUpdaterListener(offlineBookShopService, bookStorageService);
    }

    @Test
    void onChequeSaves_BookStorage() {
        Book book = new Book(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), "Книга 1", "Автор1", BookGenre.NOVEL, "1999");
        SoldBook soldBook = new SoldBook(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), "Книга1", 3);
        BookStorage expectedStorage = new BookStorage(UUID.fromString("bc48ea89-c708-44d7-a1e2-1fa66635531c"), "qwerty", "qwerty", 0);
        BookInfo bookInfo = new BookInfo(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), book, 50, BigDecimal.valueOf(10), expectedStorage, null);
        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("c6408153-41fa-41e4-a718-51317e879141"))
                .email("assd")
                .password("fggf")
                .build();

        Order order = new Order(UUID.randomUUID(), customer.getId(), customer.getEmail(), expectedStorage.getId(), null, List.of(soldBook), OrderStatus.ACCEPTED, BigDecimal.valueOf(100));
        Cheque cheque = Cheque.builder()
                .id(UUID.fromString("f8652e48-8ae3-4937-b018-28038fabe8ee"))
                .onlineShopPurchase(expectedStorage)
                .order(order)
                .chequeType(ChequeType.ONLINE)
                .build();

        expectedStorage.addBook(bookInfo);

        UUID expectedStorageId = expectedStorage.getId();


        when(bookStorageService.getStorageById(expectedStorage.getId())).thenReturn(expectedStorage);
        when(bookStorageService.showAllBookStorageIds()).thenReturn(List.of(expectedStorageId));

        bookStorageUpdaterListener.onChequeSaved(cheque);

        verify(offlineBookShopService, never()).getStorageById(any());

        Assertions.assertEquals(47, expectedStorage.getTotalAmount());
        assertEquals(47, expectedStorage.getTotalAmount());

    }


    @Test
    void onChequeSaves_OfflineBookShop() {

        BuyBookDto buyBookDtoTwo = new BuyBookDto(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), "Книга1", 3);
        Book book = new Book(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), "Книга 1", "Автор1", BookGenre.NOVEL, "1999");
        SoldBook soldBook = new SoldBook(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), "Книга1", 3);
        OfflineBookShop offlineBookShop = new OfflineBookShop(UUID.fromString("bc48ea89-c708-44d7-a1e2-1fa66635531c"), "qwerty", "qwerty", 0);
        BookInfo bookInfo = new BookInfo(UUID.fromString("afeb88e3-5e2d-482d-a5f4-e3cb065d0a79"), book, 50, BigDecimal.valueOf(10), null, offlineBookShop);
        GetUserDto customer = GetUserDto.builder()
                .id(UUID.fromString("c6408153-41fa-41e4-a718-51317e879141"))
                .email("assd")
                .password("fggf")
                .build();

        Order order = new Order(UUID.randomUUID(), customer.getId(),customer.getEmail(), null, offlineBookShop.getId(), List.of(soldBook), OrderStatus.ACCEPTED, BigDecimal.valueOf(100));
        Cheque cheque = Cheque.builder()
                .id(UUID.fromString("f8652e48-8ae3-4937-b018-28038fabe8ee"))
                .offlineShopPurchase(offlineBookShop)
                .order(order)
                .chequeType(ChequeType.ONLINE)
                .build();

        offlineBookShop.addBook(bookInfo);

        when(offlineBookShopService.getStorageById(offlineBookShop.getId())).thenReturn(offlineBookShop);
        when(offlineBookShopService.showAllBookStorageIds()).thenReturn(List.of(offlineBookShop.getId()));

        bookStorageUpdaterListener.onChequeSaved(cheque);

        int amountAfterPurchase = offlineBookShop.getTotalAmount();

        Assertions.assertEquals(47, amountAfterPurchase);
        Assertions.assertEquals(47, amountAfterPurchase);
        verify(bookStorageService, never()).getStorageById(any());
    }
}
