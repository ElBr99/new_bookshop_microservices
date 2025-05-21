package ru.ifellow.jschool.ebredichina.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

// мне почему-то название класса говорит о том, что это наследник Exception-а)
// лучше переименовать в ExceptionResponse или даже ErrorResponse, чтобы было понятно, что это не вываливающееся исключение, а логичный бизнесовый ответ
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private String message;
    private Map<String, List<String>> errors;

}
