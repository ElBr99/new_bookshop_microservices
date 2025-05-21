package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Entity
@ToString(exclude = {"books"})
@Table(name = "book_storage")
public class BookStorage extends AbsStorage {

    @OneToMany(mappedBy = "bookStorage")
    @MapKey(name = "id")
    private Map<UUID, BookInfo> books = new HashMap<>();


    @Builder
    public BookStorage(UUID id, String address, String name, Integer totalAmount) {
        super(id, address, name, totalAmount);
    }
}
