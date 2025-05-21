package ru.ifellow.ebredichina.bookservice.model;

import java.util.Map;
import java.util.UUID;

public interface StorageEntity {

    Map<UUID, BookInfo> getBooks();
}
