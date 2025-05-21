package ru.ifellow.jschool.ebredichina.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ifellow.jschool.exception.BookNotFoundException;
import ru.ifellow.jschool.exception.BookStorageNotFoundException;
import ru.ifellow.jschool.exception.OfflineBookShopNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {
        Map<String, List<String>> newMapErr = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            newMapErr.computeIfAbsent(fieldName, k -> new ArrayList<String>()).add(errorMessage);
        });


        ErrorResponse response = ErrorResponse.builder()
                .message("Validation failed")
                .errors(newMapErr)
                .build();

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }


    @ExceptionHandler({
            BookStorageNotFoundException.class,
            OfflineBookShopNotFoundException.class,
            BookNotFoundException.class,
            BadCredentialsException.class,
            AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {

        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AuthorizationDeniedException exception) {
        ErrorResponse responseException = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseException);
    }


}



