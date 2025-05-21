package ru.ifellow.ebredichina.userserice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import ru.ifellow.ebredichina.userserice.enums.UserRole;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = {"orders"})
@Entity
@DiscriminatorValue("Customer")
public class Customer extends User {

//    @OneToMany(mappedBy = "customer")
//    private List<Order> orders;
//
//    @OneToMany(mappedBy = "user")
//    private List<FavouriteBook> favourites;

    @Builder
    public Customer(UUID id, String email, String name, String password, UserRole userRole) {
        super(id, email, name, password, userRole);
    }

}
