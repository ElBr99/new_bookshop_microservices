package ru.ifellow.jschool.ebredichina.listener;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifellow.jschool.ebredichina.model.BookStorage;
import ru.ifellow.jschool.ebredichina.model.Cheque;
import ru.ifellow.jschool.ebredichina.model.OfflineBookShop;
import ru.ifellow.jschool.ebredichina.model.SoldBook;
import ru.ifellow.jschool.ebredichina.service.impl.CommonStorageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class BookStorageUpdaterListener implements ChequeListener {

    private final CommonStorageService<OfflineBookShop> offlineBookShopService;
    private final CommonStorageService<BookStorage> bookStorageService;

    @Override
    public void onChequeSaved(Cheque cheque) {
        List<SoldBook> customerBox = cheque.getOrder().getCustomerBox();

        Optional<Integer> customerBookAmount = customerBox.stream()
                .map(SoldBook::getAmount)
                .reduce(Integer::sum);

        customerBookAmount.ifPresent(amount -> {
            if (cheque.getOfflineShopPurchase() != null) {
                UUID purchasePlaceIdOffline = cheque.getOfflineShopPurchase().getId();
                List<UUID> bookShopIds = offlineBookShopService.showAllBookStorageIds();
                bookShopIds.stream()
                        .filter(id -> id.equals(purchasePlaceIdOffline))
                        .map(offlineBookShopService::getStorageById)
                        .forEach(offlineBookShop -> {
                            customerBox.forEach(book -> {
                                UUID bookId = book.getBookId();
                                offlineBookShop.removeBook(bookId, book.getAmount());
                            });
                        });


            } else {
                UUID purchasePlaceIdOnline = cheque.getOnlineShopPurchase().getId();
                List<UUID> bookStorageIds = bookStorageService.showAllBookStorageIds();

                bookStorageIds.stream()
                        .filter(id -> id.equals(purchasePlaceIdOnline))
                        .map(bookStorageService::getStorageById)
                        .forEach(bookStorage -> {
                            customerBox.forEach(book -> {
                                UUID bookId = book.getBookId();
                                bookStorage.removeBook(bookId, book.getAmount());
                            });
                        });
            }
        });
    }
}

