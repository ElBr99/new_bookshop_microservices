package ru.ifellow.jschool.orderservice.listener;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifellow.jschool.client.BookStorageClient;
import ru.ifellow.jschool.client.OfflineBookShopClient;
import ru.ifellow.jschool.dto.SoldBook;
import ru.ifellow.jschool.orderservice.model.Cheque;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class BookStorageUpdaterListener implements ChequeListener {

    private final OfflineBookShopClient offlineBookShopClient;
    private final BookStorageClient bookStorageClient;

    @Override
    public void onChequeSaved(Cheque cheque) {
        List<SoldBook> customerBox = cheque.getOrder().getCustomerBox();

        Optional<Integer> customerBookAmount = customerBox.stream()
                .map(SoldBook::getAmount)
                .reduce(Integer::sum);

        customerBookAmount.ifPresent(amount -> {
            if (cheque.getOfflinePurchaseId() != null) {
                customerBox.forEach(book -> {
                    UUID bookId = book.getBookId();
                    offlineBookShopClient.removeBook(cheque.getOfflinePurchaseId(), bookId, book.getAmount());
                });
            } else {
                customerBox.forEach(book -> {
                    UUID bookId = book.getBookId();
                    bookStorageClient.removeBook(cheque.getOnlinePurchaseId(), bookId, book.getAmount());
                });
            }
        });
    }
}

