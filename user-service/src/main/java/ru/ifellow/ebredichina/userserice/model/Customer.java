package ru.ifellow.ebredichina.userserice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.UserRole;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Customer")
public class Customer extends User {

    @Builder
    public Customer(UUID id, String email, String name, String password, UserRole userRole) {
        super(id, email, name, password, userRole);
    }

}
