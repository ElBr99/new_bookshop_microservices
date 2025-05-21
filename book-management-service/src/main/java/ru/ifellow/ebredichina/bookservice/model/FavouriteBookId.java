package ru.ifellow.ebredichina.bookservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavouriteBookId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "book_info_id")
    private UUID bookInfoId;
}
