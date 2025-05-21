package ru.ifellow.ebredichina.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifellow.ebredichina.bookservice.model.Book;


import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
