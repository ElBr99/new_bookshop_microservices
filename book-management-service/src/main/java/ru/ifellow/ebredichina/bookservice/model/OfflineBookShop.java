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
@Entity
@ToString(exclude = {"books"})
@RequiredArgsConstructor
@Table(name = "offline_bookshop")
public class OfflineBookShop extends AbsStorage {

    @OneToMany(mappedBy = "offlineBookShop")
    @MapKey(name = "id")
    private Map<UUID, BookInfo> books = new HashMap<>();

    @Builder
    public OfflineBookShop(UUID id, String address, String name, Integer totalAmount) {
        super(id, address, name, totalAmount);
    }

}
