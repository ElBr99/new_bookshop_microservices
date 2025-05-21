package ru.ifellow.ebredichina.bookservice.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifellow.ebredichina.bookservice.exception.BookStorageNotFoundException;
import ru.ifellow.ebredichina.bookservice.exception.OfflineBookShopNotFoundException;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.ebredichina.bookservice.repository.BookStorageRepository;
import ru.ifellow.ebredichina.bookservice.repository.OfflineBookShopRepository;

@Configuration
public class StorageServiceResolver {

    @Bean
    public CommonStorageService<OfflineBookShop> offlineBookShopCommonStorageService(OfflineBookShopRepository offlineBookShopRepository) {
        return new CommonStorageService<>(offlineBookShopRepository, () -> new OfflineBookShopNotFoundException("Такой магазин не найден"));
    }

    @Bean
    public CommonStorageService<BookStorage> bookStorageCommonStorageService(BookStorageRepository bookStorageRepository) {
        return new CommonStorageService<>(bookStorageRepository, () -> new BookStorageNotFoundException("Такой склад не найден"));
    }
}
