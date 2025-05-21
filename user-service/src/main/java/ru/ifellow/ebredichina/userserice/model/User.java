package ru.ifellow.ebredichina.userserice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.ifellow.ebredichina.userserice.enums.UserRole;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@DiscriminatorColumn(columnDefinition = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;

    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "users_role")
    private UserRole userRole;

}
