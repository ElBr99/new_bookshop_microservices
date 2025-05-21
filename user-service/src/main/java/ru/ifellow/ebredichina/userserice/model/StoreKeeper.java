package ru.ifellow.ebredichina.userserice.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import ru.ifellow.ebredichina.userserice.enums.UserRole;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("StoreKeeper")
public class StoreKeeper extends User {


    @Column(name = "book_storage_id")
    private UUID workPlace;

    @Builder
    public StoreKeeper(UUID id, String email, String name, String password, UserRole userRole) {
        super(id, email, name, password, userRole);
    }
}
