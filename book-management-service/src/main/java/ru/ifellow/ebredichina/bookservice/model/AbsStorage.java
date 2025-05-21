package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbsStorage implements StorageEntity {

    @Id
    @UuidGenerator
    protected UUID id;

    protected String address;
    protected String name;

    @Column(name = "total_amount")
    protected Integer totalAmount;

    public void enlargeTotalAmount(Integer amount) {
        totalAmount = totalAmount + amount;
    }

    public void lowerTotalAmount(Integer amount) {
        totalAmount = totalAmount - amount;
    }


    public void addBook(BookInfo bookInfo) {
        this.getBooks()
                .values()
                .stream()
                .filter(bookInfo1 -> checkBook(bookInfo, bookInfo1))
                .findFirst()
                .ifPresentOrElse(
                        bookInfo1 -> bookInfo1.setAmount(bookInfo1.getAmount() + bookInfo.getAmount()),
                        () -> this.getBooks().put(bookInfo.getId(), bookInfo)
                );

        this.enlargeTotalAmount(bookInfo.getAmount());

    }

    private static boolean checkBook(BookInfo bookInfo, BookInfo bookInfo1) {
        String bookInfoTitle = bookInfo.getBook().getTitle();
        String bookInfo1Title = bookInfo1.getBook().getTitle();
        String bookInfoPublishingYear = bookInfo.getBook().getPublishingYear();
        String bookInfo1PublishingYear = bookInfo1.getBook().getPublishingYear();

        return bookInfo1Title.equals(bookInfoTitle) && bookInfo1PublishingYear.equals(bookInfoPublishingYear);
    }


    public void removeBook(UUID id, Integer amountForDelete) {

        this.getBooks().computeIfPresent(id, (key, existingBook) -> {
            int existingBookAmount = existingBook.getAmount();

            if (existingBookAmount > amountForDelete) {
                existingBook.setAmount(existingBookAmount - amountForDelete);
                this.lowerTotalAmount(amountForDelete);
                return existingBook;
            } else if (existingBookAmount == amountForDelete) {
                this.lowerTotalAmount(existingBookAmount);
                return null;
            } else {
                throw new IllegalArgumentException("Недостаточно экземпляров книги для удаления");
            }
        });
    }

}

