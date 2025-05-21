package ru.ifellow.ebredichina.bookservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookFinderDto {

    @Size(min = 3, max = 200, message = "Название книги должно быть более 3-х символов, но менее 200")
    private String bookTitle;

    @Size(min = 3, max = 100, message = "Имя автора должно быть более 3-х символов, но менее 100")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$", message = "Имя автора должно содержать только буквы и пробелы")
    private String author;
}
