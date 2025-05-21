package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifellow.jschool.enums.UserRole;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUserDto {

    private UUID id;
    private String email;
    private String name;
    private String password;
    private UserRole userRole;
    private UUID workPlace;

}
