package ru.ifellow.ebredichina.userserice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto {

    @NotBlank(message = "Поле не должно быть пустым")
    @Email(message = "Email должен быть корректным и содержать символ '@'.Например:customer@mail.ru")
    private String email;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 6, max = 12)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).+$",
            message = "Пароль должен содержать как минимум одну цифру, одну строчную букву, одну заглавную букву и один из символов: @, #, $, %.")
    private String password;

    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]+$", message = "Имя должно содержать только буквы.")
    private String name;

}
