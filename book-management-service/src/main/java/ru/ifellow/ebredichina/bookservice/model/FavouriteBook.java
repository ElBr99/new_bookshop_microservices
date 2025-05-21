package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_favorite_books")
public class FavouriteBook {

    @EmbeddedId
    private FavouriteBookId id;

    @Column(name = "user_id", insertable=false, updatable=false)
    private UUID userId;

    @ManyToOne
    @MapsId("bookInfoId")
    @JoinColumn(name = "book_info_id")
    private BookInfo bookInfoFavourite;


}
