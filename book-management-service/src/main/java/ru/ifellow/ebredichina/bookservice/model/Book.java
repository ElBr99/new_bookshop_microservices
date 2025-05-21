package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.ifellow.ebredichina.bookservice.enums.BookGenre;


import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(name = "publishing_year")
    private String publishingYear;

}
