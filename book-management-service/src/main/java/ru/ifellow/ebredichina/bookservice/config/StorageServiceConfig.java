package ru.ifellow.ebredichina.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.ebredichina.bookservice.model.OfflineBookShop;
import ru.ifellow.ebredichina.bookservice.repository.BookStorageRepository;
import ru.ifellow.ebredichina.bookservice.repository.OfflineBookShopRepository;
import ru.ifellow.ebredichina.bookservice.service.impl.CommonStorageService;
import ru.ifellow.jschool.exception.BookStorageNotFoundException;
import ru.ifellow.jschool.exception.OfflineBookShopNotFoundException;

@Configuration
public class StorageServiceConfig {

    @Bean
    public CommonStorageService<OfflineBookShop> offlineBookShopCommonStorageService(OfflineBookShopRepository offlineBookShopRepository) {
        return new CommonStorageService<OfflineBookShop>(offlineBookShopRepository, () -> new OfflineBookShopNotFoundException("Такой магазин не найден"));
    }

    @Bean
    public CommonStorageService<BookStorage> bookStorageCommonStorageService(BookStorageRepository bookStorageRepository) {
        return new CommonStorageService<BookStorage>(bookStorageRepository, () -> new BookStorageNotFoundException("Такой склад не найден"));
    }
}
